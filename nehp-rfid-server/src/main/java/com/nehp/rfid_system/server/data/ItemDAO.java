package com.nehp.rfid_system.server.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.Item;

import io.dropwizard.hibernate.AbstractDAO;

public class ItemDAO extends AbstractDAO<Item>{
	
	private SessionFactory factory;
	
	public ItemDAO(SessionFactory sessionFactory) {
		super(sessionFactory);	
		factory = sessionFactory;
	}
	
	public Optional<Item> getItemById(Long id){
		return Optional.of(get(id));
		//return list(namedQuery("items.getById").setParameter("id", id, StringType.INSTANCE)).get(0);
	}
	
	public Optional<Item> getItemByRFID(String id){
		return Optional.of(list(namedQuery("items.getByRFID")
				.setParameter("rfid", id, StringType.INSTANCE)).get(0));
		
	}
	
	public List<Item> getItemsByStage(String type){
		if(type.equals("all") || type.equals("ALL"))
			return getItemsAll();
		return list(namedQuery("items.getByStage").setParameter("stage", type, StringType.INSTANCE));
	}
	
	public List<Item> getItemsAll(){
		return list(namedQuery("items.getAll"));
	}
	
	public List<Item> getMultipleItemsById(List<Long> list){
		Iterator<Long> iter = list.listIterator();
		List<Item> filtered = new ArrayList<Item>();
		while(iter.hasNext()){
			Item item = get(iter.next());
			if ( item != null ) {
				filtered.add(item);
			}
		}
		return filtered;
	}
	
	public Long create(Item item){
		return persist(item).getId();
	}
	
	public boolean update(Item item){
		// Make sure we update the correct user
		Item updateItem = get(item.getId());
		
		if(updateItem == null)
			return false;
		
		updateItem.setCurrentStage(item.getCurrentStage());
		updateItem.setCurrentRevision(item.getCurrentRevision());
		updateItem.setCurrentRevisionDate(item.getCurrentRevisionDate());
		updateItem.setDescription(item.getDescription());
		updateItem.setRFID(item.getRFID());
		updateItem.setLastStatusChangeDate(item.getLastStatusChangeDate());
		updateItem.setLastStatusChangeUser(item.getLastStatusChangeUser());
		updateItem.setStage0Date(item.getStage0Date());
		updateItem.setStage0User(item.getStage0User());
		updateItem.setStage1Date(item.getStage1Date());
		updateItem.setStage1User(item.getStage1User());
		updateItem.setStage2Date(item.getStage2Date());
		updateItem.setStage2User(item.getStage2User());
		updateItem.setStage3Date(item.getStage3Date());
		updateItem.setStage3User(item.getStage3User());
		updateItem.setStage4Date(item.getStage4Date());
		updateItem.setStage4User(item.getStage4User());
		updateItem.setStage5Date(item.getStage5Date());
		updateItem.setStage5User(item.getStage5User());
		updateItem.setStage6Date(item.getStage6Date());
		updateItem.setStage6User(item.getStage6User());
		updateItem.setStage7Date(item.getStage7Date());
		updateItem.setStage7User(item.getStage7User());
		persist(updateItem);

		return true;	
	}
	
	// TODO: Create a method to update many items at once
	// should return an array that tells which items were
	// successfully updated.
	
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
