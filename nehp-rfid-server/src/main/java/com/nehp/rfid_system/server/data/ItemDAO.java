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
	
	/**
	 * Gets an by its unique id.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Item> getItemById(Long id){
		return Optional.of(get(id));
	}
	
	/**
	 * Gets an item by its RFID tag.
	 * 
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Gets an item by its drawing number.
	 * 
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Get a list of items by the drawing number.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<List<Item>> getItemsByItemId(String id){
		return Optional.of(list(namedQuery("items.getByItemId")
				.setParameter("id", id, StringType.INSTANCE)));
	}
	
	/**
	 * Get a list of items by their printed status.
	 * 
	 * @param printed
	 * @return
	 */
	public Optional<List<Item>> getItemsByPrinted(Boolean printed){
		return Optional.of(list(namedQuery("items.getByPrinted")
				.setParameter("printed", printed)));
	}
	
	/**
	 * Get an item by the drawing number and revision.
	 * 
	 * @param id
	 * @param rev
	 * @return
	 */
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
	
	/**
	 * Get item by its stage value.
	 * 
	 * @param type
	 * @return
	 */
	public Optional<List<Item>> getItemsByStage(String type){
		int stage = 0;
		if(type.equals("all") || type.equals("ALL"))
			return getItemsAll();
		else
			stage = Integer.parseInt(type);
		
		return Optional.of(list(namedQuery("items.getByStage").setParameter("stage", stage, IntegerType.INSTANCE)));
	}
	
	/**
	 * Get a list of all items.
	 * 
	 * @return
	 */
	public Optional<List<Item>> getItemsAll(){
		return Optional.of(list(namedQuery("items.getAll")));
	}
	
	/**
	 * Get a list of items according to a list of their unique IDs.
	 * 
	 * @param list
	 * @return
	 */
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
	
	/**
	 * Create a new item.
	 * 
	 * @param item
	 * @return
	 */
	public Long create(Item item){
		return persist(item).getId();
	}
	
	/**
	 * Update an item.
	 * 
	 * @param item
	 * @return
	 */
	public boolean update(Item item){
		// Make sure we update the correct item
		Item updateItem;
		if (item.getId() > 0)
			updateItem = get(item.getId());
		else 
			updateItem = this.getItemByItemIdAndRev(item.getItemId(), item.getRevision()).get();
		
		if(updateItem == null)
			return false;
		
		updateItem.setGroup(item.getGroup());
		updateItem.setCurrentStage(item.getCurrentStage());
		updateItem.setCurrentStageDesc(item.getCurrentStageDesc());
		updateItem.setRevision(item.getRevision());
		updateItem.setReason(item.getReason());
		updateItem.setComment(item.getComment());
		updateItem.setPrinted(item.getPrinted());
		updateItem.setRFID(item.getRFID());
		updateItem.setLastStatusChangeDate(item.getLastStatusChangeDate());
		updateItem.setLastStatusChangeUser(item.getLastStatusChangeUser());
		persist(updateItem);

		return true;	
	}
	
	
	/**
	 * Delete an item.
	 * 	
	 * @param item
	 * @return
	 */
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
