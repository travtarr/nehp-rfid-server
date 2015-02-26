package com.nehp.rfid_system.server.data;

import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;

import com.nehp.rfid_system.server.core.Setting;

public class SettingDAO extends AbstractDAO<Setting>{

	private SessionFactory factory;

	public SettingDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		factory = sessionFactory;
	}
	
	public List<Setting> getAll(){
		return list(namedQuery("setting.getAll"));
	}
	
	public Setting getById(Long id){
		return get(id);
	}
	
	public List<Setting> getByUserId(Long id){
		return list(namedQuery("setting.getByUser").setParameter("user", id, LongType.INSTANCE));
	}
	
	public Long create(Setting setting){
		return persist(setting).getId();
	}
	
	/**
	 * Updates the setting record.<br>
	 * 
	 * 
	 * @param id
	 * @param setting
	 * @return
	 */
	public boolean update(Setting setting){
		if (setting.getId() == null)
			return false;
		// Make sure we update the correct setting
		Setting update = get(setting.getId());

		if (update == null){
			return false;
		}
		
		update.setDuration(setting.getDuration());
		
		persist(update);
		
		return true;
	}
	
	public boolean deleteById(Long id){
		if(delete(get(id)))
			return true;
		else
			return false;
	}
	
	public boolean delete(Setting setting) {
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(setting);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}
	
	public boolean updateAllUnlessUserChanged(List<Setting> list){
		
		boolean success = false;
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Setting.class);
			criteria.add(Restrictions.eq("userChanged", false));
			ScrollableResults settings = criteria.scroll();
			int count = 0;
			while ( settings.next() ) {
				Setting setting = (Setting) settings.get(0);
				for ( Setting newSetting : list ) {
					if ( setting.getStage() == newSetting.getStage() ){
						setting.setDuration( newSetting.getDuration() );
					}
					
				}

				session.saveOrUpdate(setting);
				if( ++count % 100 == 0 ) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			success = true;
		} catch ( HibernateException ex ){
			if ( tx != null) {
				tx.rollback();
			}
			success = false;
		} finally {
			session.close();
		}
		
		return success;
	}
}
