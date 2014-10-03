package com.nehp.rfid_system.server.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.nehp.rfid_system.server.core.Notifications;

import io.dropwizard.hibernate.AbstractDAO;

public class NotificationsDAO extends AbstractDAO<Notifications>{
	
	private SessionFactory factory;
	
	public NotificationsDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		factory = sessionFactory;
	}
	
	public Notifications getNotificationById(Long id){
		return get(id);
	}
	
	public List<Notifications> getAll(){
		return list(namedQuery("notifications.getAll"));
	}
	
	public Long create(Notifications notification){
		return persist(notification).getId();
	}
	
	public boolean delete(Notifications notification){
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(notification);
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
