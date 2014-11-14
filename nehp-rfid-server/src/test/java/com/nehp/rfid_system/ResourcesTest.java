package com.nehp.rfid_system;

import static org.fest.assertions.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.io.File;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.io.Resources;
import com.nehp.rfid_system.server.MainApp;
import com.nehp.rfid_system.server.MainConfiguration;
import com.sun.jersey.api.client.ClientResponse;


public class ResourcesTest {
	
	private static final long USERID = 1;

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
}
