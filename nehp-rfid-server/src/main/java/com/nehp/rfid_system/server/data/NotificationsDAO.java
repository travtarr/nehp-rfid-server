package com.nehp.rfid_system.server.data;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.nehp.rfid_system.server.core.Notification;

import io.dropwizard.hibernate.AbstractDAO;

public class NotificationsDAO extends AbstractDAO<Notification> {

	private SessionFactory factory;

	public NotificationsDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		factory = sessionFactory;
	}

	/**
	 * Get by the ID of the notification.
	 * 
	 * @param id
	 * @return
	 */
	public Notification getById(Long id) {
		return get(id);
	}

	/**
	 * Get all notifications.
	 * 
	 * @return
	 */
	public List<Notification> getAll() {
		return list(namedQuery("notifications.getAll"));
	}

	/**
	 * Create a new notification.
	 * 
	 * @param notification
	 * @return
	 */
	public Long create(Notification notification) {
		return persist(notification).getId();
	}

	/**
	 * Update the notification.
	 * 
	 * @param id
	 * @param notification
	 * @return
	 */
	public boolean update(Long id, Notification notification) {
		// Make sure we update the correct notification
		Notification update = get(id);

		if (update == null)
			return false;

		update.setTitle(notification.getTitle());
		update.setMessage(notification.getMessage());
		update.setCreatedBy(notification.getCreatedBy());
		persist(update);

		return true;
	}

	/**
	 * Delete by the id of the notification.
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteById(Long id) {
		if (delete(get(id)))
			return true;
		else
			return false;
	}

	/**
	 * Delete based upon the actual notification.
	 * 
	 * @param notification
	 * @return
	 */
	public boolean delete(Notification notification) {
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(notification);
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
}
