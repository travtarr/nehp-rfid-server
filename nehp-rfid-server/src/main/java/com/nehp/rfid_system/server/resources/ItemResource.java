package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.ItemWrap;
import com.nehp.rfid_system.server.data.ItemDAO;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

	private final ItemDAO items;
	
	public ItemResource(ItemDAO itemdao){
		this.items = itemdao;
	}
		
	@GET
	@Timed
	@Path("/{id}")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER) 
	public Item getItem(@PathParam("id") String id){
		return items.getItemById(Long.parseLong(id)).get();	
	}
	
	@GET
	@Timed
	@Path("/item-rev/{item_id}/{rev}")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Item getItem(@PathParam("item_id") String itemId, @PathParam("rev") String rev){
		return items.getItemByItemIdAndRev(itemId, rev).get();
	}
	
	@POST
	@Timed
	@Path("/rfid")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Item getItemByRFID(String rfid){
		if (!rfid.isEmpty() || rfid != "null")
			return items.getItemByRFID(rfid).get();
		else
			return null;
	}
	
		
	@PUT
	@Timed
	@Path("/{id}")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public Response updateItem( @PathParam("id") String id, Item item ){
		// Verify IDs match
		if (Long.parseLong(id) != item.getId())
			return Response.status( Response.Status.BAD_REQUEST ).build();
		
		boolean updated = items.update(item);
		if (updated) {
				ItemWrap wrap = new ItemWrap();
				Item updatedItem = items.getItemById( Long.parseLong(id) ).get();
				wrap.setItem(updatedItem);
				return Response.status( Response.Status.OK ).entity( wrap ).build();
		} else {
			return Response.status( Response.Status.NOT_MODIFIED ).build();
		}
	}
	
	@GET
	@Timed
	@Path("/{id}/delete")
	@UnitOfWork
	@Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	@RestrictedTo(Authority.ROLE_ADMIN)
	public String deleteItem(@PathParam("id") String id){
		Item item = items.getItemById(Long.getLong(id)).get();
		
		if(item != null){
			if(items.delete(item))
				return "Item: " + id + " updated successfully";
			else
				return "Item: " + id + " was not deleted";
			
		} else {	
			return "Item: " + id + " was not deleted";
		}
	}
	
	@PUT
	@Timed
	@Path("/create")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response createItem( Item item ){
		Long itemId = null;
		itemId = items.create(item);
		
		if(itemId != null)
			return Response.status( Response.Status.OK ).build();
		else
			return Response.status( Response.Status.BAD_REQUEST ).build();
	}
}
