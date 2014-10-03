package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.AccessTokenDAO;
import com.nehp.rfid_system.server.data.UserDAO;
import com.sun.jersey.api.Responses;

@Path("/auth/token")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
	private ImmutableList<String> allowedGrantTypes;
	private AccessTokenDAO accessTokenDAO;
	private UserDAO userDAO;
	
	public AuthResource(ImmutableList<String> allowedGrantTypes, AccessTokenDAO accessTokenDAO, UserDAO userDAO){
		this.allowedGrantTypes = allowedGrantTypes;
		this.accessTokenDAO = accessTokenDAO;
		this.userDAO = userDAO;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@UnitOfWork
	public String postForToken(
					@FormParam("grant_type") String grantType,
					@FormParam("username") String username,
					@FormParam("password") String password
	){
		System.out.println("grant_type=" + grantType);
		System.out.println("username=" + username);
		System.out.println("password=" + password);
		
		// Check if the grant type is allowed
		if(!allowedGrantTypes.contains(grantType)){
			Response response = Response.status(Responses.METHOD_NOT_ALLOWED).build();
			throw new WebApplicationException(response);
		}
		
		// Try to find the user
		Optional<User> user = userDAO.getUserByUsernameAndPassword(username, password);
		if (user == null || !user.isPresent()){
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		
		// User found, generate token
		AccessToken accessToken = accessTokenDAO.generateNewAccessToken(user.get().getId(),  new DateTime());
		
		return accessToken.getId().toString();
	}
}
