package com.nehp.rfid_system.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.data.ItemDAO;


@Path("status/item/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

	private final ItemDAO items;
	
	public ItemResource(ItemDAO itemdao){
		this.items = itemdao;
	}
	
	@GET
	@Timed
	public Item getItem(@PathParam("id") String id){
		return items.getItemById(Long.getLong(id));	
	}
	
	@GET
	@Timed
	@Path("/update")
	public void updateItem(@PathParam("id") String id){

	}
	
	@GET
	@Timed
	@Path("/delete")
	public void deleteItem(@PathParam("id") String id){
		
	}
	
	@GET
	@Timed
	@Path("/create")
	public void createItem(@PathParam("id") String id){
		
	}
}
