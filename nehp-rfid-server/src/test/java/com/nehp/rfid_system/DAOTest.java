package com.nehp.rfid_system;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.Notification;
import com.nehp.rfid_system.server.core.User;

public class DAOTest {

	SessionFactory sessionFactory;
	
	public DAOTest(){
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.url", "jdbc:mysql://ec2-54-85-61-143.compute-1.amazonaws.com/test");
		config.setProperty("hibernate.connection.username", "webapp");
		config.setProperty("hibernate.connection.password", "5FKL0923pyTm4");
		config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		config.setProperty("hibernate.current_session_context_class",  "thread");
		config.setProperty("hibernate.show_sql", "false");
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(Item.class);
		config.addAnnotatedClass(AccessToken.class);
		config.addAnnotatedClass(Notification.class);
				
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}
	
	public Session getSession(){
		Session session;
		
		try {
			session = sessionFactory.getCurrentSession();
		} catch (SessionException se) {
			session = sessionFactory.openSession();
		}
		
		return session;
	}
}
