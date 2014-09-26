package com.nehp.rfid_system.server.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
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
	public List<Item> getList(@PathParam("type") String type){
		List<Item> list = null;

		
		// figure out which list to give client
		switch(ListType.valueOf(type)){
		case ALL:
			list = items.getItemsAll();
			break;
		case ARRIVAL:
			list = items.getItemsByStage(type);
			break;
		case INSTALLED:
			list = items.getItemsByStage(type);
			break;
		case KITTING:
			list = items.getItemsByStage(type);
			break;
		case MANUFACTURING:
			list = items.getItemsByStage(type);
			break;
		case QAQC:
			list = items.getItemsByStage(type);
			break;
		case MODELING:
			list = items.getItemsByStage(type);
			break;
		case SHIPPED:
			list = items.getItemsByStage(type);
			break;
		case STOPPED:
			list = items.getItemsByStage(type);
			break;
		default:
			break;
		}
				
		return list;
	}
}
