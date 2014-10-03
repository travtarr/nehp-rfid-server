package com.nehp.rfid_system.server.data;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.User;

public class UserDAO extends AbstractDAO<User> {

	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<User> getUserByUsernameAndPassword(String username,
			String password) {
		Optional<User> user = null;

		user = Optional.of(list(namedQuery("users.getByUsernameAndPassword")
						.setParameter("username", username,StringType.INSTANCE)
						.setParameter("password", password,StringType.INSTANCE)).get(0));
		return user;
	}

	public User getUserById(Long id) {
		User user = null;
		user = get(id);
		return user;
	}

	public Long create(User user) {
		return persist(user).getId();
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
		return list(namedQuery("user.getAll"));
	}

	public boolean delete(User user) {
		return delete(user);
	}
}
