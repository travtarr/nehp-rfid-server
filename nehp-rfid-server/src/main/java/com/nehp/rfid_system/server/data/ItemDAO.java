package com.nehp.rfid_system.server.data;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import com.nehp.rfid_system.server.core.Item;

import io.dropwizard.hibernate.AbstractDAO;

public class ItemDAO extends AbstractDAO<Item>{

	public ItemDAO(SessionFactory sessionFactory) {
		super(sessionFactory);	
	}
	
	public Item getItemById(Long id){
		return get(id);
		//return list(namedQuery("items.getById").setParameter("id", id, StringType.INSTANCE)).get(0);
	}
	
	public List<Item> getItemsByStage(String type){
		return list(namedQuery("items.getByType").setParameter("stage", type, StringType.INSTANCE));
	}
	
	public List<Item> getItemsAll(){
		return list(namedQuery("items.getAll"));
	}
}
