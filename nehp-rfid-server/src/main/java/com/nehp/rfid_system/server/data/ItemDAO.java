package com.nehp.rfid_system.server.data;

import java.util.ArrayList;
import java.util.Date;
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
			updateItem = this.getItemByItemIdAndRev(item.getItemId(), item.getCurrentRevision()).get();
		
		if(updateItem == null)
			return false;
		
		updateItem.setGroup(item.getGroup());
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
	
	public boolean sendNextStage(Item item, String modifier){

		boolean status = true;
		switch (item.getCurrentStageNum()){
		
			case Stages.STAGE_NOT_SET_NUM:
				item.setCurrentStageNum(Stages.STAGE1_NUM);
				item.setCurrentStage(Stages.STAGE1);
				item.setStage1Date(new Date());
				item.setStage1User(modifier);
				break;
			case Stages.STAGE1_NUM:
				item.setCurrentStageNum(Stages.STAGE2_NUM);
				item.setCurrentStage(Stages.STAGE2);
				item.setStage2Date(new Date());
				item.setStage2User(modifier);
				break;
			case Stages.STAGE2_NUM:
				item.setCurrentStageNum(Stages.STAGE3_NUM);
				item.setCurrentStage(Stages.STAGE3);
				item.setStage3Date(new Date());
				item.setStage3User(modifier);
				break;
			case Stages.STAGE3_NUM:
				item.setCurrentStageNum(Stages.STAGE4_NUM);
				item.setCurrentStage(Stages.STAGE4);
				item.setStage4Date(new Date());
				item.setStage4User(modifier);
				break;
			case Stages.STAGE4_NUM:
				item.setCurrentStageNum(Stages.STAGE5_NUM);
				item.setCurrentStage(Stages.STAGE5);
				item.setStage5Date(new Date());
				item.setStage5User(modifier);
				break;
			case Stages.STAGE5_NUM:
				item.setCurrentStageNum(Stages.STAGE6_NUM);
				item.setCurrentStage(Stages.STAGE6);
				item.setStage6Date(new Date());
				item.setStage6User(modifier);
				break;
			case Stages.STAGE6_NUM:
				item.setCurrentStageNum(Stages.STAGE7_NUM);
				item.setCurrentStage(Stages.STAGE7);
				item.setStage7Date(new Date());
				item.setStage7User(modifier);
				break;
			case Stages.STAGE7_NUM:
				status = false;
				break;
			case Stages.STAGE0_NUM:
				status = false;
				break;
		}
		boolean updated = false;
		if (status) {
			updated = this.update(item);
		}
		
		return updated;
	}
	
	public boolean sendToShipping(Item item, String modifier){
		
		if (item.getCurrentStageNum() == Stages.STAGE2_NUM) {
				item.setCurrentStageNum(Stages.STAGE7_NUM);
				item.setCurrentStage(Stages.STAGE7);
				item.setStage7Date(new Date());
				item.setStage7User(modifier);
		}

		boolean updated = this.update(item);

		
		return updated;
	}
	
	
	public void updateGroup(Item item, String user){		
		Optional<List<Item>> optItems = this.getItemsByItemId(item.getItemId());
		
		if ( optItems.isPresent() ) {
			List<Item> updateItems = optItems.get();
			
			// need to set every other revision to ON HOLD status
			for ( Item itemFromList : updateItems ){
				if ( itemFromList.getCurrentStageNum() != Stages.STAGE0_NUM 
						&& !itemFromList.getCurrentRevision().equals(item.getCurrentRevision())){
					itemFromList.setCurrentStageNum(Stages.STAGE0_NUM);
					itemFromList.setCurrentStage(Stages.STAGE0);
					itemFromList.setStage0Date(new Date());
					itemFromList.setStage0User(user);
					persist(itemFromList);
				}
			}
		}
		
		persist(item);
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
