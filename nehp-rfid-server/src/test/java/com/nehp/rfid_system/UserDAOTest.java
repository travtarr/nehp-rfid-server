package com.nehp.rfid_system;

import static org.fest.assertions.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.UserDAO;

public class UserDAOTest extends DAOTest{

	UserDAO userDAO;
	
	@Before
	public void initialize() {
		userDAO = new UserDAO(sessionFactory);
	}
	
	@Test
	public void testGetUserByUsernameAndPassword(){
		Long id = 1L;
		String username = "alpha";
		String password = "alpha";
		String email = "alpha@alpha.com";
		String name = "Alpha";
		DateTime loginDate = new DateTime(2012, 4, 23, 22, 25, 43);
		DateTime createdDate = new DateTime(2012, 2, 22, 19, 22, 21);
		
		getSession().beginTransaction();
		User user = userDAO.getUserByUsernameAndPassword(username, password).get();
		getSession().getTransaction().commit();
		
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getUsername()).isEqualTo(username);
		assertThat(user.getPassword()).isEqualTo(password);
		assertThat(user.getLastLoginDate()).isEqualTo(loginDate);
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getUserCreatedDate()).isEqualTo(createdDate);
	}
	
	@Test
	public void testGetUserById(){
		Long id = 1L;
		String username = "alpha";
		
		getSession().beginTransaction();
		User user = userDAO.getUserById(id);
		getSession().getTransaction().commit();
		
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getUsername()).isEqualTo(username);
	}
}
