package com.nehp.rfid_system.server.resources;

import java.util.UUID;

import io.dropwizard.hibernate.UnitOfWork;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Setting;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.core.UserList;
import com.nehp.rfid_system.server.core.UserWrap;
import com.nehp.rfid_system.server.data.AccessTokenDAO;
import com.nehp.rfid_system.server.data.SettingDAO;
import com.nehp.rfid_system.server.data.UserDAO;
import com.sun.jersey.api.Responses;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private final long GLOBAL_SETTINGS_ID = 1L;
	
	private final UserDAO userDAO;
	private final AccessTokenDAO accessTokenDAO;
	private final SettingDAO settingDAO;
	
	public UserResource(UserDAO user, AccessTokenDAO accessTokenDAO, SettingDAO settingDAO){
		this.userDAO = user;
		this.accessTokenDAO = accessTokenDAO;
		this.settingDAO = settingDAO;
	}
	
	@GET
	@Timed
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_ADMIN) 
	public UserList getAllUsers(){
		UserList list = new UserList();
		list.setUsers(userDAO.getUsersAll());
		return list;
	}
	
	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_ADMIN) 
	public Response createUser ( UserWrap user ){
		//User newUser = user.getUser();
		Long userId = null;
		Optional<Long> opt = userDAO.create( user.getUser() );
		if(opt.isPresent()){
			userId = opt.get();
		}
		if ( userId != null ) {
			/* Create Settings Profile for new user */
			// grab global settings profile
			Setting setting = settingDAO.getById(GLOBAL_SETTINGS_ID);
			setting.setUser(userId);
			setting.setUserChanged(false);
			settingDAO.create(setting);
			
			UserWrap wrap = new UserWrap();
			User newUser = userDAO.getUserById( userId ).get();
			wrap.setUser( newUser );
			
			return Response.status( Response.Status.CREATED ).entity( wrap ).build();
		} else
			return Response.status( Response.Status.BAD_REQUEST ).build();
	}
	
	@DELETE
	@Timed
	@Path("/{user_id}")
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_ADMIN) 
	public JsonObject deleteUser(@PathParam("user_id") String userId){
		if(userDAO.deleteById(Long.parseLong(userId)))
			return Json.createObjectBuilder().build();
		else {
			Response response = Response.status(Responses.NOT_MODIFIED).build();
			throw new WebApplicationException(response);
		}
	}
	
	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user_id}")
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_USER})
	public UserWrap getUser( @PathParam("user_id") String userId, @Context HttpServletRequest request ){
		
		UUID accessTokenUUID = getUUID(request.getHeader(HttpHeaders.AUTHORIZATION));
		UserWrap wrap = new UserWrap();
		if(accessTokenUUID != null){
			// User can only get information about oneself
			if((accessTokenDAO.findAccessTokenById(accessTokenUUID).get().getUserId()) == Long.parseLong(userId))
				wrap.setUser(userDAO.getUserById(Long.parseLong(userId)).get());
		}
		return wrap;
	}
	
	
	@PUT
	@Timed
	@Path("/{user_id}")
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_USER, Authority.ROLE_ADMIN})
	public Response updateUser( @PathParam("user_id") String userId, UserWrap user, @Context HttpServletRequest request){
		
		UUID accessTokenUUID = getUUID(request.getHeader(HttpHeaders.AUTHORIZATION));
		Long currentUserId = accessTokenDAO.findAccessTokenById(accessTokenUUID).get().getUserId();
		User currentUser = userDAO.getUserById(currentUserId).get();
		
		// User can only update own information, admin can update anyone
		if( ( currentUserId == Long.getLong(userId) ) || currentUser.getAdmin() == true ){
			if( userDAO.update( user.getUser() ) ){
				UserWrap wrap = new UserWrap();
				User updatedUser = userDAO.getUserById( Long.getLong(userId) ).get();
				wrap.setUser(updatedUser);
				return Response.status( Response.Status.OK ).entity( wrap ).build();
			} else {
				return Response.status( Response.Status.BAD_REQUEST ).build();
			}
		} else {
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
	
	/**
	 * Gets the UUID from the access token.
	 * @param accessToken
	 * @return
	 */
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
	
	
	@SuppressWarnings("unused")
	private void sendMail(String senderEmail, String receiverEmail, String message, String title){
		
	}
}
