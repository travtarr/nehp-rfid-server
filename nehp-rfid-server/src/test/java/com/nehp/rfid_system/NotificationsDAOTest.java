package com.nehp.rfid_system;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.nehp.rfid_system.server.core.Notification;
import com.nehp.rfid_system.server.data.NotificationsDAO;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class NotificationsDAOTest extends DAOTest {
	
	NotificationsDAO dao;
	
	@Before
	public void initialize() {
		dao = new NotificationsDAO(sessionFactory);
	}
	
	@Test
	public void getAll(){
		String title = "Welcome";
		
		getSession().beginTransaction();
		List<Notification> notifications = dao.getAll();
		getSession().getTransaction().commit();
		
		assertThat(notifications.get(0).getTitle()).isEqualTo(title);
	}
	
	@Test
	public void getById(){
		String title = "Welcome";
		Long id = 1L;

		getSession().beginTransaction();
		Notification notifications = dao.getById(id);
		getSession().getTransaction().commit();
		
		System.out.println(notifications.getDate().toString());
		assertThat(notifications.getTitle()).isEqualTo(title);
	}
	
	@Test
	public void createNotification() {
		String title = "Hello";
		String msg = "This is another test";
		String createdBy = "alpha";
		Notification notification = new Notification();
		notification.setCreatedBy(createdBy);
		notification.setTitle(title);
		notification.setMessage(msg);
		
		getSession().beginTransaction();
		Long newId = dao.create(notification);
		getSession().getTransaction().commit();
		
		getSession().beginTransaction();
		Notification newNotification = dao.getById(newId);
		getSession().getTransaction().commit();
		
		System.out.println(newNotification.getDate().toString());
		assertNotNull(newNotification);
		
		getSession().beginTransaction();
		boolean delete = dao.deleteById(newId);
		getSession().getTransaction().commit();
		
		assertThat(delete).isTrue();
	}
}
