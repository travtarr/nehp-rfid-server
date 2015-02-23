package com.nehp.rfid_system.server.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Group;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.ItemList;
import com.nehp.rfid_system.server.core.ItemMin;
import com.nehp.rfid_system.server.core.ItemMinList;
import com.nehp.rfid_system.server.core.StageLog;
import com.nehp.rfid_system.server.data.GroupDAO;
import com.nehp.rfid_system.server.data.ItemDAO;
import com.nehp.rfid_system.server.data.StageLogDAO;
import com.nehp.rfid_system.server.helpers.Stages;

@Path("items")
public class ItemsResource {
	
	private final static String ON_HOLD_REASON_NEW_REVISION = "New Revision";

	private final ItemDAO items;
	private final GroupDAO groups;
	private final StageLogDAO stages;

	public ItemsResource(ItemDAO itemdao, GroupDAO groupDAO, StageLogDAO stageDAO) {
		this.items = itemdao;
		this.groups = groupDAO;
		this.stages = stageDAO;
	}

	@GET
	@Timed
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public ItemList getItemList() {
		ItemList list = new ItemList();
		Optional<List<Item>> retrievedList = items.getItemsAll();
		if (retrievedList.isPresent())
			list.setItems(retrievedList.get());
		return list;
	}
	
	@GET
	@Timed
	@Path("/printed/{isPrinted}")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public ItemList getItemListByPrinted(@PathParam("isPrinted") String isPrinted) {
		Boolean printed = false;
		if (isPrinted.equals("true"))
			printed = true;
			
		ItemList list = new ItemList();
		Optional<List<Item>> retrievedList = items.getItemsByPrinted(printed);
		if (retrievedList.isPresent())
			list.setItems(retrievedList.get());
		return list;
	}
	

	@GET
	@Timed
	@Path("/multi")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ItemList getItemListByList(List<Long> longs) {
		ItemList list = new ItemList();
		list.setItems(items.getMultipleItemsById(longs));
		return list;
	}

	/**
	 * Creates a list of items. Only returns the created items. Items that
	 * failed to be created are not sent. Calling application should verify all
	 * returned items match all sent items.
	 * 
	 * @param itemList
	 * @return a list of of the items that were created
	 */
	@POST
	@Timed
	@Path("/create")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public Response createItem(ItemList itemList) {

		if (itemList == null) {
			System.out.println("createItem: itemList is null");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	
		List<Item> itemArray = itemList.getItems();
		List<Item> duplicatedItems = new ArrayList<Item>();
		for (Item item : itemArray) {
			// If this is a new revision, need to remove the group id from old
			// revisions and add it to this new revision
			Long group = null;
			Optional<List<Item>> revList = items.getItemsByItemId(item.getItemId());
			if (revList.isPresent() && revList.get().size() > 0){
				for (Item revItem : revList.get()){
					Optional<StageLog> revStageLogOpt = stages.getById(revItem.getCurrentStage());
					if ( revStageLogOpt.isPresent() 
							&& revStageLogOpt.get().getStage() != Stages.STAGE0_NUM ) {
						
						if (revItem.getGroup() != null){
							group = revItem.getGroup();
							revItem.setGroup(null);
						}
						
						Long revStage = this.setHold(revItem, ON_HOLD_REASON_NEW_REVISION, item.getCreatedBy());
						revItem.setCurrentStage(revStage);
						revItem.setCurrentStageNum(Stages.STAGE0_NUM);
						revItem.setCurrentStageDesc(Stages.STAGE0);
						items.update(revItem);
					}
				}
			}
			item.setCurrentStageNum(Stages.STAGE1_NUM);
			item.setCurrentStageDesc(Stages.STAGE_POST_STATUS[Stages.STAGE1_NUM]);
			item.setCreatedDate(new Date());
			item.setLastStatusChangeDate(new Date());
			item.setLastStatusChangeUser(item.getCreatedBy());
			if (group != null)
				item.setGroup(group);
			
			// Make sure this item is not already created
			if (!items.getItemByItemIdAndRev(item.getItemId(), item.getRevision()).isPresent()) {
				Long createdId = items.create(item);
				Item createdItem = items.getItemById(createdId).get();
				
				// Create stage
				StageLog newStage = new StageLog();
				newStage.setItem(createdId);
				newStage.setSignedBy(item.getCreatedBy());
				newStage.setStage(Stages.STAGE1_NUM);
				newStage.setDescription(Stages.STAGE1);
				Long newStageId = stages.create(newStage);
				
				// Update item with created stage
				createdItem.setCurrentStage(newStageId);
				items.update(createdItem);
			} else {
				duplicatedItems.add(item);
			}
		}

		if (duplicatedItems.size() > 0) {
			// Send the items that are duplicates back to the client
			return Response.status(Response.Status.OK).entity(duplicatedItems)
					.build();
		} else {
			return Response.status(Response.Status.OK).build();
		}
		
	}
	
	/**
	 * This resource gets a list of drawings and if they exist, creates a group
	 * and assigns that group's ID to each drawing.
	 * 
	 * @param list
	 * @return If all drawings don't match, returns a list of the ones 
	 * 		   that weren't found.
	 */
	@POST
	@Timed
	@Path("/group/{user}")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public Response groupItems(@PathParam("user") String user, List<String> list) {
		
		// Verify all given items are in the database, fail if even one is wrong
		List<Item> itemList = new ArrayList<Item>();
		List<String> failedList = new ArrayList<String>();
		for (String whole : list){
			String[] split = whole.split(":");
			String itemid = split[0];
			String revision = split[1];
			Optional<Item> optItem = items.getItemByItemIdAndRev(itemid, revision);
			if ( optItem.isPresent() ) {
				Item item = optItem.get();
				// if item wasn't found, add to failed list
				if ( item == null ) {
					failedList.add(itemid + ":" + revision);
				} else {
					itemList.add(item);
				}
			} else {
				failedList.add(itemid + ":" + revision);
			}
		}
		
		if (failedList.size() > 0){
			return Response.status(Response.Status.BAD_REQUEST).entity(failedList).build();
		}
		
		// Create Group, then update each item with group's ID
		Group group = new Group();
		group.setCreatedBy(user.trim());
		group.setCreatedDate(new Date());
		Long groupId = groups.create(group);
		if (groupId == null){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		// Add Group to each item, then update each item in the DB
		for (Item itemToUpdate : itemList){
			itemToUpdate.setGroup(groupId);
			items.update(itemToUpdate);
		}
		return Response.status(Response.Status.OK).build();
	}

	/**
	 * Updates the given items.
	 * 
	 * @param itemList
	 * @return only items that fail to update are returned
	 */
	@POST
	@Timed
	@Path("/multi")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public Response updateItems(ItemList itemList) {

		if (itemList.getItems().size() < 1) {
			System.out.println("Bad list or empty list");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		boolean updated = false;
		List<Item> failedList = new ArrayList<Item>();
		for (Item item : itemList.getItems()) {
			Item oldItem = items.getItemById(item.getId()).get();
			item.setLastStatusChangeDate(new Date());
			//
			// TODO: If stage has changed, create new stage
			//
			item.setCurrentStageDesc( Stages.STAGE_POST_STATUS[item.getCurrentStageNum()] );
			
			if ( item.getCurrentStageNum() != oldItem.getCurrentStageNum() ){
				Long newStageId = this.newStage(item.getCurrentStageNum(), item.getId(), 
						"admin", item.getReason(), Stages.STAGES[item.getCurrentStageNum()] );
				item.setCurrentStage(newStageId);
			}
			
			updated = items.update(item);
			if (!updated) {
				failedList.add(item);
			} 
		}
		if (failedList.size() != 0) {
			ItemList failedItemList = new ItemList();
			failedItemList.setItems(failedList);
			return Response.status(Response.Status.OK).entity(failedItemList)
					.build();
		} else {
			return Response.status(Response.Status.OK).build();
		}
	}
	
	/**
	 * Sends the given items to the next stage.
	 * 
	 * @param itemList
	 * @return only items that fail to update are returned
	 */
	@POST
	@Timed
	@Path("/nextStage")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public Response sendNextStage(ItemMinList list) {

		if (list.getItemMins().size() < 1) {
			System.out.println("Bad list or empty list");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		boolean success = false;
		List<ItemMin> failedList = new ArrayList<ItemMin>();
		Long created = null;
		for (ItemMin itemMin : list.getItemMins()) {
			Item item = null;
			Optional<Item> opt = items.getItemByItemIdAndRev(itemMin.getItemId(), itemMin.getRevision());
			if (opt.isPresent())
				item = opt.get();
			
			if (item != null) {
				created = setNextStage(item, itemMin.getModifier());
			}
			
			if (created == null) {
				failedList.add(itemMin);
			} else {
				item.setCurrentStage(created);
				StageLog newStage = stages.getById( created ).get();
				item.setCurrentStageNum( newStage.getStage() );
				item.setCurrentStageDesc( Stages.STAGE_POST_STATUS[newStage.getStage()] );
				item.setReason(itemMin.getReason());
				items.update(item);
				success = true;
			}	
		}
		if (failedList.size() != 0) {
			ItemMinList failedItemList = new ItemMinList();
			failedItemList.setItemMins(failedList);
			return Response.status(Response.Status.OK).entity(failedItemList)
					.build();
		} else if (success) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.NOT_MODIFIED).build();
		}
	}
	
	@POST
	@Timed
	@Path("/ToShipping")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public Response sendToShipping(ItemMinList list) {

		if (list.getItemMins().size() < 1) {
			System.out.println("Bad list or empty list");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		List<ItemMin> failedList = new ArrayList<ItemMin>();
		Long created = null;
		for (ItemMin itemMin : list.getItemMins()) {
			Item item = null;
			
			Optional<Item> opt = items.getItemByItemIdAndRev(itemMin.getItemId(), itemMin.getRevision());
			if (opt.isPresent())
				item = opt.get();
			
			if (item != null) {
				created = setShipping(item, itemMin.getModifier());
			}
			StageLog newStage = stages.getById(created).get();
			if (created == null) {
				failedList.add(itemMin);
			} else {
				item.setCurrentStage(created);
				item.setCurrentStageDesc( Stages.STAGE_POST_STATUS[newStage.getStage()] );
				item.setCurrentStageNum( newStage.getStage() );
			}
		}
		if (failedList.size() != 0) {
			ItemMinList failedItemList = new ItemMinList();
			failedItemList.setItemMins(failedList);
			return Response.status(Response.Status.OK).entity(failedItemList)
					.build();
		} else {
			return Response.status(Response.Status.OK).build();
		}
	}
	
	private Long setNextStage(Item item, String signedBy) {
		if (item.getCurrentStage() == null){
			return newStage(1, item.getId(), signedBy, null, Stages.STAGE1);
		}	
		Optional<StageLog> stageOpt = stages.getById(item.getCurrentStage());
		if (stageOpt.isPresent()){
			int curStage = stageOpt.get().getStage();
			if (curStage > 0 && curStage < 7) {
				return newStage(curStage + 1, item.getId(), signedBy, null, Stages.STAGES[curStage + 1]);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	private Long setShipping(Item item, String signedBy) {
		if (item.getCurrentStage() == null){
			return null;
		}	
		Optional<StageLog> stageOpt = stages.getById(item.getCurrentStage());
		if (stageOpt.isPresent()){

			int curStage = stageOpt.get().getStage();
			if (curStage > 0 && curStage < 7) {
				return newStage(Stages.STAGE6_NUM, item.getId(), signedBy, null, Stages.STAGE6);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	private Long setHold(Item item, String reason,  String signedBy) {
		if (item.getCurrentStage() == null){
			return newStage(1, item.getId(), signedBy, null, Stages.STAGE0);
		}	
		Optional<StageLog> stageOpt = stages.getById(item.getCurrentStage());
		if (stageOpt.isPresent()){
			int curStage = stageOpt.get().getStage();
			if (curStage > 0 && curStage < 7) {
				return newStage(Stages.STAGE0_NUM, item.getId(), signedBy, reason, Stages.STAGE0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	private Long newStage(int stageNum, Long item, String signedBy, String reason, String description) {
		StageLog stage = new StageLog();
		stage.setItem(item);
		stage.setReason(reason);
		stage.setSignedBy(signedBy);
		stage.setStage(stageNum);
		stage.setDescription(description);
		return stages.create(stage);
	}
}
