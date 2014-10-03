package com.nehp.rfid_system.server.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.UserDAO;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private final UserDAO userDAO;
	
	public UserResource(UserDAO user){
		this.userDAO = user;
	}
	
	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user_id}")
	@UnitOfWork
	public User getUser(@Auth @PathParam("user_id") String userId){
		return userDAO.getUserById(Long.getLong(userId));
	}
	
	@GET
	@Timed
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public String createUser(@Auth User user){
		Long userId = null;
		userId = userDAO.create(user);
		if(userId != null)
			return "User: " + user.getUsername() + " created successfully with id: " + userId;
		else
			return "User: " + user.getUsername() + " was not created";
	}
	
	@GET
	@Timed
	@Path("/{user_id}/update")
	@Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	@UnitOfWork
	public String updateUser(@Auth @PathParam("user_id") String userId, User user){
		if(userDAO.update(user))
			return "User: " + user.getUsername() + " updated successfully";
		else
			return "User: " + user.getUsername() + " was not updated";
	}
	
	@GET
	@Timed
	@Path("/{user_id}/delete")
	@UnitOfWork
	public String deleteUser(@Auth @PathParam("user_id") String userId){
		User user = userDAO.getUserById(Long.getLong(userId));
		
		if(user != null){
			if(userDAO.delete(user))
				return "User: " + user.getUsername() + " updated successfully";
			else
				return "User: " + user.getUsername() + " was not deleted";
			
		} else {	
			return "UserID: " + userId + " was not deleted";
		}
	}
}
