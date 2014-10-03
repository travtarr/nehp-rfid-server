package com.nehp.rfid_system;

import com.google.common.io.Resources;
import com.nehp.rfid_system.server.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import io.dropwizard.testing.junit.DropwizardAppRule;

import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import java.io.File;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;

public class AuthTest {
	private final static String NOT_ACCEPTED_ACCESS_TOKEN = "00000000-0000-0000-0000-000000000000";

	private static final String acceptedGrantType = "password";
	private static final String acceptedUsername = "alpha";
	private static final String acceptedPassword = "alpha";

	@ClassRule
	public static final DropwizardAppRule<MainConfiguration> RULE;

	static {
		try {
			RULE = new DropwizardAppRule<>(MainApp.class, new File(Resources.getResource("config.yml").toURI()).getAbsolutePath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGivenHappyPathWhenTokenRequestedThenAccessTokenIsValidUUID() throws Exception {
		String entity = accessToken();
		assertThat(entity).isEqualTo(UUID.fromString(entity).toString());
	}

	@Test
	public void pingReturns200AndPong() {
		ClientResponse response = get("/ping");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("{\"answer\": \"pong\"}");
	}

	@Test
	public void authPingWithoutAccessTokenReturns401() {
		ClientResponse response = get("/ping/auth");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("Credentials are required to access this resource.");
	}

	@Test
	public void authPingWithNotAcceptedAccessTokenReturns401() {
		ClientResponse response = get("/ping/auth", NOT_ACCEPTED_ACCESS_TOKEN);
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("Credentials are required to access this resource.");
	}

	@Test
	public void authPingWithIncorrectAccessTokenReturns401() {
		ClientResponse response = get("/ping/auth", "zzzzz");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("Credentials are required to access this resource.");
	}

	@Test
	public void authPingWithAccessTokenReturns200AndPong() {
		ClientResponse response = get("/ping/auth", accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("{\"answer\": \"authenticated pong for user 1\"}");
	}

	private ClientResponse get(final String endPoint) {
		return new Client()
				.resource(String.format("http://localhost:%d/service%s", RULE.getLocalPort(), endPoint))
				.get(ClientResponse.class);
	}

	private ClientResponse get(final String endPoint, final String accessToken) {
		return new Client()
				.resource(String.format("http://localhost:%d/service%s", RULE.getLocalPort(), endPoint))
				.header("Authorization", String.format("Bearer %s", accessToken))
				.get(ClientResponse.class);
	}

	private String accessToken() {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("grant_type", acceptedGrantType);
		formData.add("username", acceptedUsername);
		formData.add("password", acceptedPassword);
		Client client = new Client();

		ClientResponse response = client
				.resource(String.format("http://localhost:%d/service/auth/token", RULE.getLocalPort()))
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, formData);

		assertThat(response.getStatus()).isEqualTo(200);
		return response.getEntity(String.class);
	}	
}
