package com.nehp.rfid_system.server.data;

import org.hibernate.SessionFactory;

import com.nehp.rfid_system.server.core.Items;

import io.dropwizard.hibernate.AbstractDAO;

public class ItemsDAO extends AbstractDAO<Items>{

	public ItemsDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		
	}
	
//	public Items getItemsByID(Long id){
//		
//	}

}
