package com.nehp.rfid_system.server;

import org.joda.time.DateTimeZone;

import com.nehp.rfid_system.server.auth.RestrictedToProvider;
import com.nehp.rfid_system.server.auth.SimpleAuthenticator;
import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.Notification;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.AccessTokenDAO;
import com.nehp.rfid_system.server.data.ItemDAO;
import com.nehp.rfid_system.server.data.NotificationsDAO;
import com.nehp.rfid_system.server.data.UserDAO;
import com.nehp.rfid_system.server.resources.AuthResource;
import com.nehp.rfid_system.server.resources.ItemResource;
import com.nehp.rfid_system.server.resources.ItemsResource;
import com.nehp.rfid_system.server.resources.ListResource;
import com.nehp.rfid_system.server.resources.NotificationsResource;
import com.nehp.rfid_system.server.resources.PingResource;
import com.nehp.rfid_system.server.resources.UserResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class MainApp extends Application<MainConfiguration> {

	public static void main(String[] args) throws Exception {
		new MainApp().run(args);
	}

	@Override
	public String getName() {
		return "nehp-rfid-server";
	}

	@Override
	public void initialize(Bootstrap<MainConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html", "static"));
		DateTimeZone.setDefault(DateTimeZone.UTC);
	}
	
	private HibernateBundle<MainConfiguration> hibernate = new HibernateBundle<MainConfiguration>(
			Item.class, User.class, AccessToken.class, Notification.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(MainConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void run(MainConfiguration configuration, Environment environment)
			throws ClassNotFoundException {
		
		// intialize DAOs
		ItemDAO itemDAO = new ItemDAO(hibernate.getSessionFactory());
		AccessTokenDAO accessTokenDAO = new AccessTokenDAO(hibernate.getSessionFactory());
		UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
		NotificationsDAO notificationsDAO = new NotificationsDAO(hibernate.getSessionFactory());
				
		// initialize resources
		final ListResource listResource = new ListResource(itemDAO);
		final ItemResource itemResource = new ItemResource(itemDAO);
		final ItemsResource itemsResource = new ItemsResource(itemDAO);
		final AuthResource authResource = new AuthResource(
				configuration.getAllowedGrantTypes(), accessTokenDAO, userDAO);
		final UserResource userResource = new UserResource(userDAO, accessTokenDAO);
		final NotificationsResource notificationsResource = new NotificationsResource(notificationsDAO);
		// test resource
		final PingResource pingResource = new PingResource();
		
		// register resources
		environment.jersey().setUrlPattern("/service/*");
		environment.jersey().register(listResource);
		environment.jersey().register(itemResource);
		environment.jersey().register(itemsResource);
		environment.jersey().register(authResource);
		environment.jersey().register(userResource);
		environment.jersey().register(notificationsResource);
		// test resource
		environment.jersey().register(pingResource);

		// register auth provider
		environment.jersey().register(
				new RestrictedToProvider<>(new SimpleAuthenticator(hibernate.getSessionFactory()), configuration
						.getRealm()));
	}
}
