package com.nehp.rfid_system;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.core.EmailCredentials;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.Notification;
import com.nehp.rfid_system.server.core.Setting;
import com.nehp.rfid_system.server.core.User;

public class DAOTest {

	SessionFactory sessionFactory;
	EmailCredentials emailCreds;
	
	public DAOTest(){
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.url", "jdbc:mysql://nehpdbinstance.cn60mfeskrqb.us-east-1.rds.amazonaws.com/test");
		config.setProperty("hibernate.connection.username", "nehpdb");
		config.setProperty("hibernate.connection.password", "jdos803ldk23");
		config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		config.setProperty("hibernate.current_session_context_class",  "thread");
		config.setProperty("hibernate.show_sql", "false");
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(Item.class);
		config.addAnnotatedClass(AccessToken.class);
		config.addAnnotatedClass(Notification.class);
		config.addAnnotatedClass(Setting.class);
						
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		
		// set-up local email server info
		emailCreds = new EmailCredentials();
		emailCreds.setEmailAddress("travis.tarr@nehp.com");
		emailCreds.setEmailPassword(null);
		emailCreds.setEmailUser(null);
		emailCreds.setHostName("127.0.0.1");
		emailCreds.setHostPort(25);
		emailCreds.setSSL(false);
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
