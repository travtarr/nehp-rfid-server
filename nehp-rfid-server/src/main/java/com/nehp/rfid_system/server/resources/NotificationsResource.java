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
import com.nehp.rfid_system.server.core.Notification;
import com.nehp.rfid_system.server.core.NotificationList;
import com.nehp.rfid_system.server.core.NotificationWrap;
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
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_USER})
	public NotificationList getAll(){
		NotificationList list = new NotificationList();
		list.setNotifications(notificationsDAO.getAll());
		return list;
	}
	
	@GET
	@Timed
	@Path("/{id}")
	@UnitOfWork 
	@RestrictedTo({Authority.ROLE_USER}) 
	public NotificationWrap getById(@PathParam("id") String id){
		NotificationWrap wrap = new NotificationWrap();
		wrap.setNotification(notificationsDAO.getById(Long.parseLong(id)));
		return wrap;
	}
	
	@PUT
	@Timed
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_ADMIN}) 
	public String create(Notification notification){
		Long newId = null;
		newId = notificationsDAO.create(notification);
		if(newId != null)
			return "Notificaiton: " + notification.getTitle() + " created successfully";
		else
			return "Notification: " + notification.getTitle() + " not created";
	}
	
}
