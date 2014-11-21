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
	
	public Setting getByUserId(Long id){
		return list(namedQuery("setting.getByUser").setParameter("user", id, LongType.INSTANCE)).get(0);
	}
	
	public List<Setting> getByUserIdList(Long id){
		return list(namedQuery("setting.getByUser").setParameter("user", id, LongType.INSTANCE));
	}
	
	public Long create(Setting setting){
		return persist(setting).getId();
	}
	
	/**
	 * Updates the setting record.<br>
	 * 
	 * NOTE: Only to be used by a resource without the <b>UnitOfWork</b> annotation.
	 * 
	 * @param id
	 * @param setting
	 * @return
	 */
	public boolean update(Long id, Setting setting){
		
		// Make sure we update the correct setting
		Setting update = getById(id);

		if (update == null){
			return false;
		}

		update.setStage0(setting.getStage0());
		update.setStage1(setting.getStage1());
		update.setStage2(setting.getStage2());
		update.setStage3(setting.getStage3());
		update.setStage4(setting.getStage4());
		update.setStage5(setting.getStage5());
		update.setStage6(setting.getStage6());
		update.setStage7(setting.getStage7());
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
	
	public boolean updateAllUnlessUserChanged(Setting newSetting){
		
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
				setting.setStage0(newSetting.getStage0());
				setting.setStage1(newSetting.getStage1());
				setting.setStage2(newSetting.getStage2());
				setting.setStage3(newSetting.getStage3());
				setting.setStage4(newSetting.getStage4());
				setting.setStage5(newSetting.getStage5());
				setting.setStage6(newSetting.getStage6());
				setting.setStage7(newSetting.getStage7());
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
