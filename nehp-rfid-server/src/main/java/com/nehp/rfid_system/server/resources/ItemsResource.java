package com.nehp.rfid_system.server.resources;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.ItemList;
import com.nehp.rfid_system.server.data.ItemDAO;

@Path("items")
public class ItemsResource {

	private final ItemDAO items;
	
	public ItemsResource(ItemDAO itemdao){
		this.items = itemdao;
	}
	
	@GET
	@Timed
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER) 
	public ItemList getItemList(){
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
	public ItemList getItemListByList(List<Long> longs){
		// TODO: make sure the resource can interpret the sent list of longs
		ItemList list = new ItemList();
		list.setItems(items.getMultipleItemsById(longs));
		return list;
	}
	
	@PUT
	@Timed
	@Path("/multi")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public Response updateItems( ItemList itemList ){
		
		if (itemList.getItems().size() < 1){
			System.out.println("Bad list or empty list");
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
			
		boolean updated = false;
		List<Item> failedList = new ArrayList<Item>();
		for (Item item : itemList.getItems()){
			updated = items.update(item);
			if (!updated) {
				System.out.println("Not updated");
				failedList.add(item);
			} else
				System.out.println("Updated");
		}
		if (failedList.size() != 0){
			ItemList failedItemList = new ItemList();
			failedItemList.setItems(failedList);
			return Response.status( Response.Status.OK ).entity( failedItemList ).build();
		} else { //if (failedList.size() == 0) {
			return Response.status( Response.Status.OK ).build();
		}
		//System.out.println("failedList.size(): " + failedList.size());
		//return Response.status( Response.Status.BAD_REQUEST ).build();
	}
}
