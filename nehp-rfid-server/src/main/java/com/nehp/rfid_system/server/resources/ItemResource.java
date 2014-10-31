package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Item;
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
		return items.getItemById(Long.getLong(id));	
	}
	
	@PUT
	@Timed
	@Path("/{id}/update")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_SCANNER)
	public String updateItem( @PathParam("id") String id, Item item ){
		if(items.update(item))
			return "Item: " + id + " updated successfully";
		else
			return "Item: " + id + " not updated";
	}
	
	@GET
	@Timed
	@Path("/{id}/delete")
	@UnitOfWork
	@Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	@RestrictedTo(Authority.ROLE_ADMIN)
	public String deleteItem(@PathParam("id") String id){
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
	
	@PUT
	@Timed
	@Path("/create")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_SCANNER)
	public String createItem( Item item ){
		Long itemId = null;
		itemId = items.create(item);
		
		if(itemId != null)
			return "Item: " + item.getItemId() + " was created with id: " + itemId;
		else
			return "Item: " + item.getItemId() + " was not created";
	}
}
