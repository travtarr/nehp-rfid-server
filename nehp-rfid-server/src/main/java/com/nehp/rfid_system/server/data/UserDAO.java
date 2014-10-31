package com.nehp.rfid_system.server.data;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.helpers.PasswordHelper;

public class UserDAO extends AbstractDAO<User> {
	
	SessionFactory factory;

	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.factory = sessionFactory;
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
		User newUser = user;
		try {
			newUser.setPassword(PasswordHelper.getSaltedHash(user.getPassword()));
		} catch (Exception e) {
			return Optional.absent();
		}
		return Optional.of(persist(newUser).getId());
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
	
	public boolean deleteById(Long id){
		return delete(get(id));
	}

	public boolean delete(User user) {
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}
	
	public boolean isAdmin(Long id){
		return get(id).getAdmin();
	}
}
