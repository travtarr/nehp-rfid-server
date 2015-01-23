package com.nehp.rfid_system.server.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.helpers.Stages;

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
	
	public Optional<Item> getItemByItemId(String id){
		return Optional.of(list(namedQuery("items.getByItemId")
				.setParameter("item", id, StringType.INSTANCE)).get(0));
		
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
		// Make sure we update the correct item
		Item updateItem;
		if (item.getId() > 0)
			updateItem = get(item.getId());
		else 
			updateItem = this.getItemByItemId(item.getItemId()).get();
		
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
	
	public boolean updateGroup(Item item){
		// Make sure we update the correct item
				Item updateItem;
				if (item.getId() > 0)
					updateItem = get(item.getId());
				else 
					updateItem = this.getItemByItemId(item.getItemId()).get();
				
				if(updateItem == null)
					return false;
				
				updateItem.setGroup(item.getGroup());
				persist(updateItem);

				return true;	
	}
	
	public boolean sendNextStage(Item item, String user){
		boolean updated = false;
		switch (item.getCurrentStage().toUpperCase()){
			case Stages.MODELING:
				item.setStage1Date(new Date());
				item.setStage1User(user);
				item.setCurrentStage(Stages.KITTING);
				updated = true;
				break;
			case Stages.KITTING:
				item.setStage2Date(new Date());
				item.setStage2User(user);
				item.setCurrentStage(Stages.MANUFACTURING);
				updated = true;
				break;
			case Stages.MANUFACTURING:
				item.setStage3Date(new Date());
				item.setStage3User(user);
				item.setCurrentStage(Stages.QAQC);
				updated = true;
				break;
			case Stages.QAQC:
				item.setStage4Date(new Date());
				item.setStage4User(user);
				item.setCurrentStage(Stages.SHIPPED);
				updated = true;
				break;
			case Stages.SHIPPED:
				item.setStage5Date(new Date());
				item.setStage5User(user);
				item.setCurrentStage(Stages.ARRIVED);
				updated = true;
				break;
			case Stages.ARRIVED:
				item.setStage6Date(new Date());
				item.setStage6User(user);
				item.setCurrentStage(Stages.INSTALLED);
				updated = true;
				break;
			case Stages.INSTALLED:
				item.setStage7Date(new Date());
				item.setStage7User(user);
				// if installed, don't change stage description
				updated = true;
				break;
		}
		
		// now finally update the database
		if (!this.update(item))
			updated = false;
		
		return updated;
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
