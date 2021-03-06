package com.nehp.rfid_system.server.resources;

import java.util.Date;

import io.dropwizard.hibernate.UnitOfWork;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.AccessTokenDAO;
import com.nehp.rfid_system.server.data.UserDAO;
import com.sun.jersey.api.Responses;

@Path("/auth/token")
public class AuthResource {
	private ImmutableList<String> allowedGrantTypes;
	private AccessTokenDAO accessTokenDAO;
	private UserDAO userDAO;

	public AuthResource(ImmutableList<String> allowedGrantTypes,
			AccessTokenDAO accessTokenDAO, UserDAO userDAO) {
		this.allowedGrantTypes = allowedGrantTypes;
		this.accessTokenDAO = accessTokenDAO;
		this.userDAO = userDAO;
	}

	/**
	 * Generates an access token based upon user's credentials.
	 * 
	 * @param grantType
	 *            - default grant type, always "password"
	 * @param username
	 * @param password
	 * @return Correct authorization - returns JSON object with token and user
	 *         ID
	 * @return Bad credentials - returns status 401
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public JsonObject postForToken(@FormParam("grant_type") String grantType,
			@FormParam("username") String username,
			@FormParam("password") String password) {

		// Check if the grant type is allowed
		if (!allowedGrantTypes.contains(grantType)) {
			Response response = Response.status(Responses.METHOD_NOT_ALLOWED)
					.build();
			throw new WebApplicationException(response);
		}

		// Try to find the user
		Optional<User> user = userDAO.getUserByUsernameAndPassword(username,
				password);
		if (user == null || !user.isPresent()) {
			throw new WebApplicationException(Response.status(
					Response.Status.UNAUTHORIZED).build());
		}

		// User found, generate token
		Long userId = user.get().getId();

		AccessToken accessToken = accessTokenDAO.generateNewAccessToken(userId,
				new Date());

		JsonObject jo = Json
				.createObjectBuilder()
				.add("api_key",
						Json.createArrayBuilder().add(
								Json.createObjectBuilder()
										.add("access_token",
												accessToken.getId().toString())
										.add("user_id", userId.toString())))
				.build();
		return jo;
	}
}
