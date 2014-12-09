package com.nehp.rfid_system.server.resources;

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
import com.nehp.rfid_system.server.core.ItemWrap;
import com.nehp.rfid_system.server.data.ItemDAO;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
public class ItemsResource {

	private final ItemDAO items;
	
	public ItemsResource(ItemDAO itemdao){
		this.items = itemdao;
	}
	
	@GET
	@Timed
	@UnitOfWork
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
	@RestrictedTo(Authority.ROLE_SCANNER)
	public Response updateItems( ItemList itemList ){
		
//		boolean updated = items.update(item);
//		if (updated) {
//				ItemWrap wrap = new ItemWrap();
//				Item updatedItem = items.getItemById( Long.parseLong(id) ).get();
//				wrap.setItem(updatedItem);
//				return Response.status( Response.Status.OK ).entity( wrap ).build();
//		} else {
//			return Response.status( Response.Status.BAD_REQUEST ).build();
//		}
		return Response.status( Response.Status.BAD_REQUEST ).build();
	}
}
