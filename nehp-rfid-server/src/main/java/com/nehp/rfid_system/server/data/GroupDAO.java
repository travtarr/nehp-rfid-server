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
	
	/**
	 * Get a list of all groups.
	 * 
	 * @return
	 */
	public List<Group> getGroupsAll(){
		return list(namedQuery("groups.getAll"));
	}
	
	/**
	 * Create a new group.
	 * 
	 * @param group
	 * @return
	 */
	public Long create(Group group){
		return persist(group).getId();
	}
	
	/**
	 * Get by the ID of the group.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Group> getById(long id){
		return Optional.of(get(id));
	}
}
