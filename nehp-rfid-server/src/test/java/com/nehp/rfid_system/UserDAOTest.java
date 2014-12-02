package com.nehp.rfid_system;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.UserDAO;
import com.nehp.rfid_system.server.helpers.PasswordHelper;

public class UserDAOTest extends DAOTest{

	UserDAO userDAO;
	
	@Before
	public void initialize() {
		userDAO = new UserDAO(sessionFactory, emailCreds);
	}
	
	@Test
	public void testGetUserByUsernameAndPassword() throws Exception{
		Long id = 2L;
		String username = "alpha";
		String password = "alpha";
		String email = "alpha@alpha.com";
		String name = "Alpha";
		
		User user = new User();
		
		getSession().beginTransaction();
		Optional<User> optUser = userDAO.getUserByUsernameAndPassword(username, password);
		getSession().getTransaction().commit();
		
		if(optUser != null && optUser.isPresent())
			user = optUser.get();
		
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getUsername()).isEqualTo(username);
		assertThat(PasswordHelper.check(password, user.getPassword())).isTrue();
		assertThat(user.getName()).isEqualTo(name);
	}
	
	@Test
	public void testGetUserById(){
		Long id = 2L;
		String username = "alpha";
		
		getSession().beginTransaction();
		User user = userDAO.getUserById(id).get();
		getSession().getTransaction().commit();
		
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getUsername()).isEqualTo(username);
	}
	
	@Test
	public void testGetAllUsers(){
		Long id = 2L;
		
		getSession().beginTransaction();
		List<User> userList = userDAO.getUsersAll();
		getSession().getTransaction().commit();
		
		assertThat(userList.get(1).getId()).isEqualTo(id);
		assertThat(userList.get(2).getId()).isEqualTo(3L);
	}
	
	/**
	 * Make sure local smtp server is running.
	 */
	//@Test
	public void testCreateUser(){
		// set-up new user
		User user = new User();
		user.setAdmin(false);
		user.setEmail("root@localhost.com");
		user.setName("root");
		user.setScanner(false);
		user.setUsername("root");
		user.setUserCreatedDate(new DateTime());
		
		getSession().beginTransaction();
		Optional<Long> result = userDAO.create(user);
		getSession().getTransaction().commit();
		
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isGreaterThan(0L);
	}
	
	/**
	 * Make sure local smtp server is running.
	 */
	//@Test
	public void testResetPassword(){
		Long userId = 5L;
		getSession().beginTransaction();
		boolean password = userDAO.resetPassword(userId);
		getSession().getTransaction().commit();
		
		assertThat(password).isTrue();
	}
	
	@Test
	public void testUpdateUser(){
		Long id = 5L;
		
		getSession().beginTransaction();
		User user = userDAO.getUserById(id).get();
		getSession().getTransaction().commit();
		
		getSession().beginTransaction();
		boolean success = userDAO.update(user);
		getSession().getTransaction().commit();
		
		assertThat(success).isTrue();
	}
}
