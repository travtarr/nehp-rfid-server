package com.nehp.rfid_system.server.resources;

import java.util.List;
import java.util.UUID;

import io.dropwizard.hibernate.UnitOfWork;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
import com.nehp.rfid_system.server.core.AccessToken;
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
public class UserResource {

	private final long GLOBAL_SETTINGS_ID = 1L;

	private final UserDAO userDAO;
	private final AccessTokenDAO accessTokenDAO;
	private final SettingDAO settingDAO;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public UserResource(UserDAO user, AccessTokenDAO accessTokenDAO,
			SettingDAO settingDAO) {
		this.userDAO = user;
		this.accessTokenDAO = accessTokenDAO;
		this.settingDAO = settingDAO;
	}

	@GET
	@Timed
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_ADMIN)
	public UserList getAllUsers() {
		UserList list = new UserList();
		list.setUsers(userDAO.getUsersAll());
		return list;
	}

	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_ADMIN)
	public Response createUser(UserWrap user) {

		Long userId = null;
		Optional<Long> opt = userDAO.create(user.getUser());
		if (opt.isPresent()) {
			userId = opt.get();
		}
		if (userId != null) {
			/* Create Settings Profile for new user */
			// grab global settings profile
			List<Setting> settingList = settingDAO.getByUserId(GLOBAL_SETTINGS_ID);
			for ( Setting defaultSetting : settingList ) {
				Setting setting = new Setting();
				setting.setStage(defaultSetting.getStage());
				setting.setUser(userId);
				setting.setUserChanged(false);
				setting.setDuration(defaultSetting.getDuration());
				settingDAO.create(setting);
			}
			
			User newUser = userDAO.getUserById(userId).get();
			
			UserWrap wrap = new UserWrap();
			wrap.setUser(newUser);

			return Response.status(Response.Status.CREATED).entity(wrap)
					.build();
		} else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Timed
	@Path("/{user_id}/pwreset")
	@UnitOfWork
	@RestrictedTo({ Authority.ROLE_ADMIN })
	public Response resetPassword(@PathParam("user_id") String userId,
			String verifyEmail, @Context HttpServletRequest request) {

		UUID accessTokenUUID = getUUID(request
				.getHeader(HttpHeaders.AUTHORIZATION));
		if (accessTokenUUID != null) {
			Long userIdLong = Long.parseLong(userId);

			// User can only get information about oneself unless user is an
			// admin
			AccessToken accessToken = accessTokenDAO.findAccessTokenById(
					accessTokenUUID).get();
			User user = userDAO.getUserById(userIdLong).get();

			// also make sure email given matches user
			if (((accessToken.getUserId()) == userIdLong && user.getEmail()
					.equals(verifyEmail))
					|| userDAO.isAdmin(accessToken.getUserId())) {

				if (userDAO.resetPassword(userIdLong)) {
					return Response.status(Response.Status.OK).build();
				} else {
					return Response.status(Response.Status.NOT_MODIFIED)
							.build();
				}
			}
		}
		// must not have succeeded in verifying identity
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Timed
	@Path("/pwreset-email")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@UnitOfWork
	public Response resetPasswordByEmail(@FormParam("email") String email) {
		if (email.matches(EMAIL_PATTERN)){
			User user = userDAO.getUserByEmail(email).get();

			if (userDAO.resetPassword( user.getId() )) {
				return Response.status(Response.Status.OK).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
	@POST
	@Timed
	@Path("/pwchange")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_USER})
	public Response changePassword( @FormParam("username") String username,
									@FormParam("password") String password) {
		User user = userDAO.getUserByUsername(username).get();

		if (user != null && userDAO.updatePassword( user, password )) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@DELETE
	@Timed
	@Path("/{user_id}")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_ADMIN)
	public JsonObject deleteUser(@PathParam("user_id") String userId) {
		if (userDAO.deleteById(Long.parseLong(userId)))
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
	@RestrictedTo({ Authority.ROLE_USER })
	public UserWrap getUser(@PathParam("user_id") String userId,
			@Context HttpServletRequest request) {

		UUID accessTokenUUID = getUUID(request
				.getHeader(HttpHeaders.AUTHORIZATION));
		Long currentUserId = accessTokenDAO
				.findAccessTokenById(accessTokenUUID).get().getUserId();
		User currentUser = userDAO.getUserById(currentUserId).get();
		
		UserWrap wrap = new UserWrap();
		if (accessTokenUUID != null) {
			// User can only get information about oneself unless user is an admin
			if (currentUserId == Long.parseLong(userId) || currentUser.getAdmin() == true)
				wrap.setUser(userDAO.getUserById(Long.parseLong(userId)).get());
		}
		return wrap;
	}

	@PUT
	@Timed
	@Path("/{user_id}")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo( {Authority.ROLE_USER} )
	public Response updateUser(@PathParam("user_id") String userId,
			UserWrap userWrap, @Context HttpServletRequest request) {
		System.out.println("Inside updateUser");
		UUID accessTokenUUID = getUUID(request
				.getHeader(HttpHeaders.AUTHORIZATION));
		Long currentUserId = accessTokenDAO
				.findAccessTokenById(accessTokenUUID).get().getUserId();
		User currentUser = userDAO.getUserById(currentUserId).get();
		
		// User can only update own information, admin can update anyone
		if ( currentUserId == Long.parseLong(userId) || currentUser.getAdmin() == true) {
			System.out.println("Found user");
			User user = userWrap.getUser();
			user.setId(Long.parseLong(userId));
			if (userDAO.update(user)) {
				UserWrap wrap = new UserWrap();
				User updatedUser = userDAO.getUserById( Long.parseLong(userId) )
						.get();
				wrap.setUser(updatedUser);
				return Response.status( Response.Status.OK ).entity( wrap ).build();
			} else {
				return Response.status( Response.Status.BAD_REQUEST ).build();
			}
		} else {
			return Response.status( Response.Status.UNAUTHORIZED ).build();
		}
	}

	/**
	 * Gets the UUID from the access token.
	 * 
	 * @param accessToken
	 * @return
	 */
	private UUID getUUID(String accessToken) {

		final String PREFIX = "bearer";

		if (accessToken != null) {
			final int space = accessToken.indexOf(' ');
			if (space > 0) {
				final String method = accessToken.substring(0, space);
				if (PREFIX.equalsIgnoreCase(method)) {
					final String sessionTokenSplit = accessToken
							.substring(space + 1);
					return UUID.fromString(sessionTokenSplit);
				}
			}
		}
		return null;
	}
}
