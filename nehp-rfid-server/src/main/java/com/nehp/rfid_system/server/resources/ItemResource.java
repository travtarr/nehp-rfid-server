package com.nehp.rfid_system.server.resources;

import io.dropwizard.auth.Auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.data.ItemDAO;


@Path("/status/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

	private final ItemDAO items;
	
	public ItemResource(ItemDAO itemdao){
		this.items = itemdao;
	}
	
	@GET
	@Timed
	@Path("/{id}")
	public Item getItem(@Auth @PathParam("id") String id){
		return items.getItemById(Long.getLong(id));	
	}
	
	@GET
	@Timed
	@Path("/{id}/update")
	public String updateItem(@Auth @PathParam("id") String id, Item item){
		if(items.update(item))
			return "Item: " + id + " updated successfully";
		else
			return "Item: " + id + " not updated";
	}
	
	@GET
	@Timed
	@Path("/{id}/delete")
	@Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	public String deleteItem(@Auth @PathParam("id") String id){
		Item item = items.getItemById(Long.getLong(id));
		
		if(item != null){
			if(items.delete(item))
				return "Item: " + id + " updated successfully";
			else
				return "Item: " + id + " was not deleted";
			
		} else {	
			return "Item: " + id + " was not deleted";
		}
	}
	
	@GET
	@Timed
	@Path("/create")
	public String createItem(@Auth Item item){
		Long itemId = null;
		itemId = items.create(item);
		
		if(itemId != null)
			return "Item: " + item.getItemId() + " was created with id: " + itemId;
		else
			return "Item: " + item.getItemId() + " was not created";
	}
}
