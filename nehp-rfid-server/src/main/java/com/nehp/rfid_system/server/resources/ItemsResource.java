package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.ItemList;
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
	public ItemList getItemList(@QueryParam(value = "stage") String stage){
		ItemList list = new ItemList();
		if(stage.isEmpty() || stage == null || stage.equals(null) || stage.equals("null"))
			list.setItems(items.getItemsAll());
		else
			list.setItems(items.getItemsByStage(stage));
		return list;
	}
}
