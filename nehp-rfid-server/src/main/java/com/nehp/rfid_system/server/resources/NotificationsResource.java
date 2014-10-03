package com.nehp.rfid_system.server.resources;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.core.Notifications;
import com.nehp.rfid_system.server.data.NotificationsDAO;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationsResource {
	
	private final NotificationsDAO notificationsDAO;
	
	public NotificationsResource(NotificationsDAO notificationsDAO){
		this.notificationsDAO = notificationsDAO;
	}
	
	@GET
	@Timed
	public List<Notifications> getAll(){
		return notificationsDAO.getAll();
	}
	
	@GET
	@Timed
	@Path("/{id}")
	public Notifications getById(@PathParam("id") String id){
		return notificationsDAO.getNotificationById(Long.getLong(id));
	}
	
	@GET
	@Timed
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public String create(@Auth Notifications notification){
		Long newId = null;
		newId = notificationsDAO.create(notification);
		if(newId != null)
			return "Notificaiton: " + notification.getTitle() + " created successfully";
		else
			return "Notification: " + notification.getTitle() + " not created";
	}
	
}
