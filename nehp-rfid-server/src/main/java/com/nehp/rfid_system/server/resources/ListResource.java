package com.nehp.rfid_system.server.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nehp.rfid_system.server.core.Item;


@Path("/status/list/{type}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListResource {
	
	public enum ListType {
		ALL, MODELING, KITTING, MANUFACTURING,
		MFRCOMPLETE, SHIPPED, ARRIVAL, INSTALLED,
		STOPPED
	}
	
	@GET
	public List<Item> getList(@PathParam("type") String type){
		List<Item> list = null;
		
		// figure out which list to give client
		switch(ListType.valueOf(type)){
		case ALL:
			
			break;
		case ARRIVAL:
			break;
		case INSTALLED:
			break;
		case KITTING:
			break;
		case MANUFACTURING:
			break;
		case MFRCOMPLETE:
			break;
		case MODELING:
			break;
		case SHIPPED:
			break;
		case STOPPED:
			break;
		default:
			break;
			
		}
				
		return list;
	}
}
