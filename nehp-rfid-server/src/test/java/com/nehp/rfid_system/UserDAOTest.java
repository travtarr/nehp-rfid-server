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
		userDAO = new UserDAO(sessionFactory);
	}
	
	@Test
	public void testGetUserByUsernameAndPassword() throws Exception{
		Long id = 1L;
		String username = "alpha";
		String password = "alpha";
		String email = "alpha@alpha.com";
		String name = "Alpha";
		DateTime loginDate = new DateTime(2012, 4, 23, 22, 25, 43);
		DateTime createdDate = new DateTime(2012, 2, 22, 19, 22, 21);
		
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
		assertThat(user.getLastLoginDate()).isEqualTo(loginDate);
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getUserCreatedDate()).isEqualTo(createdDate);
	}
	
	@Test
	public void testGetUserById(){
		Long id = 1L;
		String username = "alpha";
		
		getSession().beginTransaction();
		User user = userDAO.getUserById(id).get();
		getSession().getTransaction().commit();
		
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getUsername()).isEqualTo(username);
	}
	
	@Test
	public void testGetAllUsers(){
		Long id = 1L;
		
		getSession().beginTransaction();
		List<User> userList = userDAO.getUsersAll();
		
		assertThat(userList.get(0).getId()).isEqualTo(id);
		assertThat(userList.get(1).getId()).isEqualTo(2L);
	}
}
