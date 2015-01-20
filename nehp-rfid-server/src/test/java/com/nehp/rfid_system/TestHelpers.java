package com.nehp.rfid_system;

import static org.fest.assertions.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.ClassRule;

import com.nehp.rfid_system.server.MainConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class TestHelpers {

	private static final String acceptedGrantType = "password";
	private static final String acceptedUsername = "alpha";
	private static final String acceptedPassword = "alpha";
		
	@ClassRule
	public static DropwizardAppRule<MainConfiguration> RULE;
	
	public TestHelpers(DropwizardAppRule<MainConfiguration> RULE){
		TestHelpers.RULE = RULE;
	}

	protected ClientResponse get(final String endPoint) {
		return new Client()
				.resource(String.format("http://localhost:%d/service%s", RULE.getLocalPort(), endPoint))
				.get(ClientResponse.class);
	}

	protected ClientResponse get(final String endPoint, final String accessToken) {
		return new Client()
				.resource(String.format("http://localhost:%d/service%s", RULE.getLocalPort(), endPoint))
				.header("Authorization", String.format("Bearer %s", accessToken))
				.get(ClientResponse.class);
	}
	
	protected ClientResponse put(final String endPoint, final String accessToken, final Object entity) {
		return new Client()
				.resource(String.format("http://localhost:%d/service%s", RULE.getLocalPort(), endPoint))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", String.format("Bearer %s", accessToken))
				.put(ClientResponse.class, entity);
	}
	
	protected ClientResponse post(final String endPoint, final String accessToken, final Object entity) {
		return new Client()
				.resource(String.format("http://localhost:%d/service%s", RULE.getLocalPort(), endPoint))
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.header("Authorization", String.format("Bearer %s", accessToken))
				.post(ClientResponse.class, entity);
	}
	
	protected ClientResponse postJSON(final String endPoint, final String accessToken, final Object entity) {
		return new Client()
				.resource(String.format("http://localhost:%d/service%s", RULE.getLocalPort(), endPoint))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", String.format("Bearer %s", accessToken))
				.post(ClientResponse.class, entity);
	}
	
	protected String accessToken() {
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
		JSONObject obj = new JSONObject(response.getEntity(String.class));
		JSONArray array = obj.getJSONArray("api_key");
		System.out.println("userId: " + array.getJSONObject(0).getJSONObject("user_id").getString("string"));
		return array.getJSONObject(0).getJSONObject("access_token").getString("string");
	}	
	
	protected String accessToken(String grant, String user, String password) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("grant_type", grant);
		formData.add("username", user);
		formData.add("password", password);
		Client client = new Client();

		ClientResponse response = client
				.resource(String.format("http://localhost:%d/service/auth/token", RULE.getLocalPort()))
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, formData);

		assertThat(response.getStatus()).isEqualTo(200);
		JSONObject obj = new JSONObject(response.getEntity(String.class));
		JSONArray array = obj.getJSONArray("api_key");
		System.out.println("userId: " + array.getJSONObject(0).getJSONObject("user_id").getString("string"));
		return array.getJSONObject(0).getJSONObject("access_token").getString("string");
	}
	
	protected String accessTokenBadPassword(String username, String password) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("grant_type", acceptedGrantType);
		formData.add("username", username);
		formData.add("password", password);
		Client client = new Client();

		ClientResponse response = client
				.resource(String.format("http://localhost:%d/service/auth/token", RULE.getLocalPort()))
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, formData);

		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		return null;
	}	
}
