package com.nehp.rfid_system.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
	
	/**
	 * Simple status check for periodic domain health check.
	 * 
	 * @return
	 */
	@GET
	@Timed
	public String pong() {
		return "{\"answer\": \"pong\"}";
	}

	/**
	 * Simple authentication check for resources.
	 * 
	 * Used for testing purposes.
	 * 
	 * @return
	 */
	@GET
	@Timed
	@Path("/auth")
	@RestrictedTo({Authority.ROLE_USER})
	public String pongAuthenticated() {
		return String.format("{\"answer\": \"authenticated pong");
	}
}