package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.json.Json;
import javax.json.JsonObject;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Notification;
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
	public Response update(@PathParam("id") String id, @Valid NotificationWrap notification){
		Notification persistedNotification = notification.getNotification();
		boolean success = notificationsDAO.update(Long.parseLong(id), notification.getNotification());
		
		NotificationWrap wrap = new NotificationWrap();
		if( success ){
			wrap.setNotification( persistedNotification );
			return Response.status( Response.Status.OK ).entity( wrap ).build();
		} else
			return Response.status( Response.Status.BAD_REQUEST ).build();
	}
	
	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_ADMIN}) 
	public Response create ( @Valid NotificationWrap notification ){
		Notification persistedNotification = notification.getNotification();
		Long newId = notificationsDAO.create( notification.getNotification() );
		
		NotificationWrap wrap = new NotificationWrap();
		if ( newId != null ) {
			persistedNotification.setId( newId );
			wrap.setNotification( persistedNotification );
			return Response.status( Response.Status.CREATED ).entity( wrap ).build();
		} else
			return Response.status( Response.Status.BAD_REQUEST ).build();
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
