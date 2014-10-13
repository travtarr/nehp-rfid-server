package com.nehp.rfid_system.server.resources;

import java.util.UUID;

import io.dropwizard.hibernate.UnitOfWork;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.core.UserWrap;
import com.nehp.rfid_system.server.data.AccessTokenDAO;
import com.nehp.rfid_system.server.data.UserDAO;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private final UserDAO userDAO;
	private final AccessTokenDAO accessTokenDAO;
	
	public UserResource(UserDAO user, AccessTokenDAO accessTokenDAO){
		this.userDAO = user;
		this.accessTokenDAO = accessTokenDAO;
	}
	
	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user_id}")
	@UnitOfWork
	public UserWrap getUser(@RestrictedTo(Authority.ROLE_USER) @PathParam("user_id") String userId, @Context HttpServletRequest request){
		
		UUID accessTokenUUID = getUUID(request.getHeader(HttpHeaders.AUTHORIZATION));
		UserWrap wrap = new UserWrap();
		// User can only get information about oneself
		if((accessTokenDAO.findAccessTokenById(accessTokenUUID).get().getUserId()) == Long.parseLong(userId))
			wrap.setUser(userDAO.getUserById(Long.parseLong(userId)).get());
		return wrap;
	}
	
	@POST
	@Timed
	@Path("/{user_id}/update")
	@UnitOfWork
	public String updateUser(@RestrictedTo(Authority.ROLE_USER) @PathParam("user_id") String userId, User user, @Context HttpServletRequest request){
		
		UUID accessTokenUUID = getUUID(request.getHeader(HttpHeaders.AUTHORIZATION));
		
		
		
		// User can only update own information
		if((accessTokenDAO.findAccessTokenById(accessTokenUUID).get().getUserId()) == Long.getLong(userId)){
			if(userDAO.update(user))
				return "User: " + user.getUsername() + " updated successfully";
			else
				return "User: " + user.getUsername() + " was not updated";
		} else {
			return "Not allowed to update another user.";
		}
	}
	
	private UUID getUUID(String accessToken){
		
		final String PREFIX = "bearer";
	
		if (accessToken != null) {
            final int space = accessToken.indexOf(' ');
            if (space > 0) {
                final String method = accessToken.substring(0, space);
                if (PREFIX.equalsIgnoreCase(method)) {
                    final String sessionTokenSplit = accessToken.substring(space + 1);
                    return UUID.fromString(sessionTokenSplit);
                }
            }
		}
		return null;
	}
	
}
