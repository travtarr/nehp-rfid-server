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
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Group;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.ItemList;
import com.nehp.rfid_system.server.data.GroupDAO;
import com.nehp.rfid_system.server.data.ItemDAO;

@Path("items")
public class ItemsResource {

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
		list.setItems(items.getItemsAll());
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
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		List<Item> itemArray = itemList.getItems();
		List<Item> updatedItems = new ArrayList<Item>();
		for (Item item : itemArray) {
			long id = items.create(item);
			if (id > 0)
				updatedItems.add(items.getItemById(id).get());
		}

		if (updatedItems.size() > 0)
			return Response.status(Response.Status.OK).entity(updatedItems)
					.build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
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

	/**
	 * Updates a list of items to indicate they are off to the next stage.
	 * 
	 * @param user
	 * @param rfid
	 * @return an array of ints in the same order that the rfid array was in 0 -
	 *         failure, 1 - success
	 */
	@POST
	@Timed
	@Path("/nextStage/{user}")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response sendNextStageMulti(@PathParam("user") String user,
			String[] rfid) {
		int[] status = new int[rfid.length];
		for (int i = 0; i < rfid.length; i++) {
			Item item = items.getItemByRFID(rfid[i]).get();

			if (items.sendNextStage(item, user))
				status[i] = 1;
			else
				status[i] = 0;
		}

		return Response.status(Response.Status.OK).entity(status).build();
	}
}
