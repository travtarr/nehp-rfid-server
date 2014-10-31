package com.nehp.rfid_system.server.resources;

import java.util.Enumeration;

import io.dropwizard.hibernate.UnitOfWork;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.NotificationList;
import com.nehp.rfid_system.server.core.NotificationWrap;
import com.nehp.rfid_system.server.data.NotificationsDAO;
import com.sun.jersey.api.Responses;

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
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_ADMIN}) 
	public String update(@PathParam("id") String id, @Valid NotificationWrap notification, @Context HttpServletRequest request){
	
		Enumeration<String> params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = (String)params.nextElement();
		 System.out.println("[NOTIFICATION] Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
		
		if(notificationsDAO.update(notification.getNotification()))
			return "Notification: " + notification.getNotification().getTitle() + " updated successfully";
		else
			return "Notification: " + notification.getNotification().getTitle() + " was not updated";
	}
	
	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_ADMIN}) 
	public String create(@Valid NotificationWrap notification){
		Long newId = null;
		newId = notificationsDAO.create(notification.getNotification());
		if(newId != null)
			return "Notificaiton: " + notification.getNotification().getTitle() + " created successfully";
		else
			return "Notification: " + notification.getNotification().getTitle() + " not created";
	}
	
	@DELETE
	@Timed
	@Path("/{id}")
	@UnitOfWork 
	@RestrictedTo({Authority.ROLE_ADMIN}) 
	public JsonObject deleteById(@PathParam("id") String id){
		if(notificationsDAO.deleteById(Long.parseLong(id)))
			return Json.createObjectBuilder().build();
		else {
			Response response = Response.status(Responses.NOT_MODIFIED).build();
			throw new WebApplicationException(response);
		}
			
	}
	
}
