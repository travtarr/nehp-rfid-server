package com.nehp.rfid_system.server.data;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.User;

import io.dropwizard.hibernate.AbstractDAO;


public class UserDAO extends AbstractDAO<User>{
	
	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public Optional<User> getUserByUsernameAndPassword(String username, String password){
		return Optional.of(list(namedQuery("user.getByUsernameAndPassword")
				.setParameter("username", username, StringType.INSTANCE)
				.setParameter("password", password, StringType.INSTANCE)).get(0));
	}
	
	public User getUserById(Long id){
		return get(id);
	}
	
	public long create(User user){
		return persist(user).getId();
	}
	
	public boolean update(User user){		
		
		// Make sure we update the correct user
		User updateUser = get(user.getId());
		
		if(updateUser == null)
			return false;
		
		updateUser = user;
		persist(updateUser);
		
		return true;		
	}
	
	public List<User> getUsersAll(){
		return list(namedQuery("user.getAll"));
	}
	
	public void updateById(String id){
		namedQuery("users.updateByUserId").setParameter("userId", id, StringType.INSTANCE);
	}
}
