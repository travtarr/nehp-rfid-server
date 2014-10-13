package com.nehp.rfid_system;

import static org.fest.assertions.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.io.File;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.io.Resources;
import com.nehp.rfid_system.server.MainApp;
import com.nehp.rfid_system.server.MainConfiguration;
import com.nehp.rfid_system.server.core.Notification;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.UserDAO;
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
	public void authGetUserReturns200AndUserObject() {
		ClientResponse response = helper.get("/users/" + USERID, helper.accessToken());
		
		User user = new User();
		DAOTest daoTest = new DAOTest();
		UserDAO userDAO = new UserDAO(daoTest.sessionFactory);
		daoTest.getSession().beginTransaction();
		Optional<User> optUser = userDAO.getUserById(USERID);
		if(optUser != null && optUser.isPresent())
			user = optUser.get();
		daoTest.getSession().getTransaction().commit();
		
		//User resourceUser = response.getEntity(User.class);
		System.out.println(response.getEntity(String.class));
		
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		//assertThat(user.getName()).isEqualTo(resourceUser.getName());
	}
	
	@Test
	public void noficationsGetAllReturnsNotificationsAndStatus200(){
		
		ClientResponse response = helper.get("/notifications");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void notificationsGetByIdReturnsNotificationsAndStatus200(){
		ClientResponse response = helper.get("/notifications/1");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
}
