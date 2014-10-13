package com.nehp.rfid_system.server.data;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.helpers.PasswordHelper;

public class UserDAO extends AbstractDAO<User> {

	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<User> getUserByUsernameAndPassword(String username,
			String password) {
		Optional<User> user = null;
		
		user = Optional.of(list(namedQuery("users.getByUsername")
						.setParameter("username", username, StringType.INSTANCE)).get(0));
		System.out.println(user.get().getPassword());
		try {
			if(PasswordHelper.check(password, user.get().getPassword()))
				return user;
			else
				return Optional.absent();
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.absent();
		}
	}

	public Optional<User> getUserById(Long id) {
		User user = null;
		user = get(id);
		
		if(user == null)
			return Optional.absent();
				
		return Optional.of(user);
	}

	public Optional<Long> create(User user) {
		try {
			user.setPassword(PasswordHelper.getSaltedHash(user.getPassword()));
		} catch (Exception e) {
			return Optional.absent();
		}
		return Optional.of(persist(user).getId());
	}

	public boolean update(User user) {

		// Make sure we update the correct user
		User updateUser = get(user.getId());

		if (updateUser == null)
			return false;

		updateUser = user;
		persist(updateUser);

		return true;
	}

	public List<User> getUsersAll() {
		return list(namedQuery("users.getAll"));
	}

	public boolean delete(User user) {
		return delete(user);
	}
	
	public boolean isAdmin(Long id){
		return get(id).getAdmin();
	}
}
