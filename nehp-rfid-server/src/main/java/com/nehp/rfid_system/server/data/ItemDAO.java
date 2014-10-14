package com.nehp.rfid_system.server.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import com.nehp.rfid_system.server.core.Item;
import io.dropwizard.hibernate.AbstractDAO;

public class ItemDAO extends AbstractDAO<Item>{
	
	private SessionFactory factory;
	
	public ItemDAO(SessionFactory sessionFactory) {
		super(sessionFactory);	
		factory = sessionFactory;
	}
	
	public Item getItemById(Long id){
		return get(id);
		//return list(namedQuery("items.getById").setParameter("id", id, StringType.INSTANCE)).get(0);
	}
	
	public List<Item> getItemsByStage(String type){
		if(type.equals("all") || type.equals("ALL"))
			return getItemsAll();
		return list(namedQuery("items.getByStage").setParameter("stage", type, StringType.INSTANCE));
	}
	
	public List<Item> getItemsAll(){
		return list(namedQuery("items.getAll"));
	}
	
	public Long create(Item item){
		return persist(item).getId();
	}
	
	public boolean update(Item item){
		// Make sure we update the correct user
		Item updateItem = get(item.getId());
		
		if(updateItem == null)
			return false;
		
		updateItem = item;
		persist(updateItem);
		
		return true;	
	}
	
	public boolean delete(Item item){
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(item);
			tx.commit();
			result = true;
		} catch (HibernateException e){
			if( tx != null )
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
}
