package com.nehp.rfid_system.server.resources;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.UserDAO;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {
	
	private final UserDAO userDAO;
	
	public AdminResource(UserDAO user){
		this.userDAO = user;
	}
	
	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<User> getAllUsers(@RestrictedTo(Authority.ROLE_ADMIN) User user){
		return userDAO.getUsersAll();
	}
	
	@GET
	@Timed
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public String createUser(@RestrictedTo(Authority.ROLE_ADMIN) User user){
		Long userId = null;
		userId = userDAO.create(user).get();
		if(userId != null)
			return "User: " + user.getUsername() + " created successfully with id: " + userId;
		else
			return "User: " + user.getUsername() + " was not created";
	}
	
	@GET
	@Timed
	@Path("/{user_id}/delete")
	@UnitOfWork
	public String deleteUser(@RestrictedTo(Authority.ROLE_ADMIN) @PathParam("user_id") String userId){
		User user = userDAO.getUserById(Long.getLong(userId)).get();
		
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
