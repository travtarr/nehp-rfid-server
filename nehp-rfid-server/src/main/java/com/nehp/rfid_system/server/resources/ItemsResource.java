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
import com.nehp.rfid_system.server.data.GroupDAO;
import com.nehp.rfid_system.server.data.ItemDAO;

@Path("items")
public class ItemsResource {
	
	private final static String NOT_PRINTED = "NOT-PRINTED";

	private final ItemDAO items;
	private final GroupDAO groups;

	public ItemsResource(ItemDAO itemdao, GroupDAO groupDAO) {
		this.items = itemdao;
		this.groups = groupDAO;
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
		// TODO: make sure the resource can interpret the sent list of longs
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
					if (revItem.getGroup() != null){
						group = revItem.getGroup();
						revItem.setGroup(null);
						items.update(revItem);
					}
				}
			}
			
			item.setId(null);
			item.setCurrentStage(NOT_PRINTED);
			if (group != null)
				item.setGroup(group);
			
			// Make sure this item is not already created
			if (!items.getItemByItemIdAndRev(item.getItemId(), item.getCurrentRevision()).isPresent()) {
				items.create(item);
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
		for (String itemid : list){
			String trimItem = itemid.trim();
			Item item = items.getItemByItemId(trimItem).get();
			// if item wasn't found, add to failed list
			if ( item == null || !item.getItemId().equals(trimItem) ) {
				failedList.add(trimItem);
			} else {
				itemList.add(item);
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
			items.updateGroup(itemToUpdate);
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
			updated = items.update(item);
			if (!updated) {
				System.out.println("Not updated");
				failedList.add(item);
			} else
				System.out.println("Updated");
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
}
