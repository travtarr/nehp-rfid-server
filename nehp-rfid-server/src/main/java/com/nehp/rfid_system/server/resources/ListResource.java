package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.data.ItemDAO;


@Path("/status/list/{type}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListResource {
	private final ItemDAO items;
	
	public ListResource(ItemDAO itemdao){
		this.items = itemdao;
	}
	
	public enum ListType {
		ALL, MODELING, KITTING, MANUFACTURING,
		QAQC, SHIPPED, ARRIVAL, INSTALLED,
		STOPPED
	}
	
	@GET
	@Timed
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER) 
	public List<Item> getList(@PathParam("type") String stage){
		List<Item> list = null;

		Optional<List<Item>> retrievedList = items.getItemsByStage(stage);
		if (retrievedList.isPresent())
			list = retrievedList.get();
				
		return list;
	}
}
