package com.nehp.rfid_system.server.resources;

import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
	@GET
	@Timed
	public String pong() {
		return "{\"answer\": \"pong\"}";
	}

	@GET
	@Timed
	@Path("/auth")
	public String pongAuthenticated(@Auth Long userId) {
		return String.format("{\"answer\": \"authenticated pong for user %d\"}", userId);
	}
}