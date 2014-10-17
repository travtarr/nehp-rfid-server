package com.nehp.rfid_system;

import com.google.common.io.Resources;
import com.nehp.rfid_system.server.MainApp;
import com.nehp.rfid_system.server.MainConfiguration;
import com.sun.jersey.api.client.ClientResponse;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

import java.io.File;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;

public class AuthTest  {
	private final static String NOT_ACCEPTED_ACCESS_TOKEN = "00000000-0000-0000-0000-000000000000";

	@ClassRule
	public static final DropwizardAppRule<MainConfiguration> RULE;
	
	static {
		try {
			RULE = new DropwizardAppRule<>(MainApp.class, new File(Resources.getResource("config.yml").toURI()).getAbsolutePath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	TestHelpers helper = new TestHelpers(RULE);
	
	@Test
	public void testGivenHappyPathWhenTokenRequestedThenAccessTokenIsValidUUID() throws Exception {
		String entity = helper.accessToken();
		assertThat(entity).isEqualTo(UUID.fromString(entity).toString());
	}

	@Test
	public void pingReturns200AndPong() {
		ClientResponse response = helper.get("/ping");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("{\"answer\": \"pong\"}");
	}

	@Test
	public void authPingWithoutAccessTokenReturns401() {
		ClientResponse response = helper.get("/ping/auth");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("Credentials are required to access this resource.");
	}

	@Test
	public void authPingWithNotAcceptedAccessTokenReturns401() {
		ClientResponse response = helper.get("/ping/auth", NOT_ACCEPTED_ACCESS_TOKEN);
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("Credentials are required to access this resource.");
	}

	@Test
	public void authPingWithIncorrectAccessTokenReturns401() {
		ClientResponse response = helper.get("/ping/auth", "zzzzz");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		assertThat(response.getEntity(String.class)).isEqualTo("Credentials are required to access this resource.");
	}

	@Test
	public void authPingWithAccessTokenReturns200AndPong() {
		ClientResponse response = helper.get("/ping/auth", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
	
	@Test
	public void accessTokenWithBadPasswordReturns401(){
		assertThat(helper.accessTokenBadPassword("alpha", "badpassword")).isNullOrEmpty();
	}

}
