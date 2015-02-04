package com.nehp.rfid_system.server.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.IntegerType;
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
		Optional<Item> item = null;
		try {
			item = Optional.of(list(namedQuery("items.getByRFID")
					.setParameter("rfid", id, StringType.INSTANCE)).get(0));
		} catch(Exception e) {
			item = Optional.absent();
		}
		return item;
	}
	
	public Optional<Item> getItemByItemId(String id){
		Optional<Item> item = null;
		try {
			item = Optional.of(list(namedQuery("items.getByItemId")
					.setParameter("id", id, StringType.INSTANCE)).get(0));
		} catch(Exception e) {
			item = Optional.absent();
		}
		
		return item;
	}
	
	public Optional<List<Item>> getItemsByItemId(String id){
		return Optional.of(list(namedQuery("items.getByItemId")
				.setParameter("id", id, StringType.INSTANCE)));
	}
	
	public Optional<List<Item>> getItemsByPrinted(Boolean printed){
		return Optional.of(list(namedQuery("items.getByPrinted")
				.setParameter("printed", printed)));
	}
	
	public Optional<Item> getItemByItemIdAndRev(String id, String rev){
		Item item = null;
		try {
			item = list(namedQuery("items.getByItemIdAndRev")
					.setParameter("id", id, StringType.INSTANCE)
					.setParameter("rev", rev, StringType.INSTANCE)).get(0);
		} catch (Exception e) {
			
		}
		
		if (item == null)
			return Optional.absent();
		else
			return Optional.of(item);	
	}
	
	public Optional<List<Item>> getItemsByStage(String type){
		int stage = 0;
		if(type.equals("all") || type.equals("ALL"))
			return getItemsAll();
		else
			stage = Integer.parseInt(type);
		
		return Optional.of(list(namedQuery("items.getByStage").setParameter("stage", stage, IntegerType.INSTANCE)));
	}
	
	public Optional<List<Item>> getItemsAll(){
		return Optional.of(list(namedQuery("items.getAll")));
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
		updateItem.setComment(item.getComment());
		updateItem.setReason(item.getReason());
		updateItem.setPrinted(item.getPrinted());
		updateItem.setCurrentStageNum(item.getCurrentStageNum());
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
