package com.nehp.rfid_system;

import static org.fest.assertions.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.io.File;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.io.Resources;
import com.nehp.rfid_system.server.MainApp;
import com.nehp.rfid_system.server.MainConfiguration;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class ResourcesTest {
	
	private static final long USERID = 2L;

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
	public void getUserReturns200AndUserObject() {
		ClientResponse response = helper.get("/users/" + USERID, helper.accessToken());		
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
	
	@Test
	public void getUserNoTokenReturns401() {
		ClientResponse response = helper.get("/users/" + USERID);		
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
	}
	
	/*@Test
	public void updateUserInfoReturns200(){
		String user = helper.get("/users/" + USERID, helper.accessToken()).getEntity(String.class);
		ClientResponse response = helper.put("/users/" + USERID, helper.accessToken(), user);
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}*/
	
	@Test
	public void noficationsGetAllReturnsNotificationsAndStatus200(){
		ClientResponse response = helper.get("/notifications", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void noficationsGetAllBadTokenReturnsStatus401(){
		ClientResponse response = helper.get("/notifications", "asas");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void noficationsGetAllNoTokenReturnsStatus401(){
		ClientResponse response = helper.get("/notifications");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void notificationsGetByIdReturnsNotificationsAndStatus200(){
		ClientResponse response = helper.get("/notifications/1", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void notificationsGetByIdBadTokenReturnsStatus401(){
		ClientResponse response = helper.get("/notifications/1", "ddsdfs");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
	}
	
	@Test
	public void itemsGetByTypeALLReturnsItemsAndStatus200(){
		ClientResponse response = helper.get("/items", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void settingsGetByIdReturnsStatus200(){
		ClientResponse response = helper.get("/settings/2", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void changePWReturns200(){
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("grant_type", "password");
		formData.add("username", "alpha");
		formData.add("password", "alpha");
		ClientResponse response = helper.post("/users/pwchange", helper.accessToken(), formData);	
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
}
