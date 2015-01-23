package com.nehp.rfid_system.server.data;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.Group;

import io.dropwizard.hibernate.AbstractDAO;

public class GroupDAO extends AbstractDAO<Group> {

	public GroupDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public List<Group> getGroupsAll(){
		return list(namedQuery("group.getAll"));
	}
	
	public Long create(Group group){
		return persist(group).getId();
	}
	
	public Optional<Group> getById(long id){
		return Optional.of(get(id));
	}
}
