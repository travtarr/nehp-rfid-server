package com.nehp.rfid_system.server;

import java.text.SimpleDateFormat;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.joda.time.DateTimeZone;

import com.nehp.rfid_system.server.auth.RestrictedToMethodDispatchAdapter;
import com.nehp.rfid_system.server.auth.SimpleAuthenticator;
import com.nehp.rfid_system.server.core.*;
import com.nehp.rfid_system.server.data.*;
import com.nehp.rfid_system.server.filters.HttpsRedirectFilter;
import com.nehp.rfid_system.server.resources.*;
import com.sun.jersey.multipart.MultiPart;

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
	
	void addHttpsForward(ServletContextHandler handler) {
		handler.addFilter(new FilterHolder(new HttpsRedirectFilter()), "/*", EnumSet.allOf(DispatcherType.class));
	}

	@Override
	public void initialize(Bootstrap<MainConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html", "static"));
		DateTimeZone.setDefault(DateTimeZone.UTC);
	}
	
	private HibernateBundle<MainConfiguration> hibernate = new HibernateBundle<MainConfiguration>(
			Item.class, User.class, AccessToken.class, Notification.class, Setting.class, Group.class, Signature.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(MainConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void run(MainConfiguration configuration, Environment environment)
			throws ClassNotFoundException {
		
		// initialize DAOs
		ItemDAO itemDAO = new ItemDAO(hibernate.getSessionFactory());
		AccessTokenDAO accessTokenDAO = new AccessTokenDAO(hibernate.getSessionFactory());
		UserDAO userDAO = new UserDAO(hibernate.getSessionFactory(), configuration.getEmailCredentials());
		NotificationsDAO notificationsDAO = new NotificationsDAO(hibernate.getSessionFactory());
		SettingDAO settingDAO = new SettingDAO(hibernate.getSessionFactory());
		SignatureDAO signatureDAO = new SignatureDAO(hibernate.getSessionFactory());
		GroupDAO groupDAO = new GroupDAO(hibernate.getSessionFactory());
				
		// initialize resources
		final ListResource listResource = new ListResource(itemDAO);
		final ItemResource itemResource = new ItemResource(itemDAO);
		final ItemsResource itemsResource = new ItemsResource(itemDAO, groupDAO);
		final AuthResource authResource = new AuthResource(
				configuration.getAllowedGrantTypes(), accessTokenDAO, userDAO);
		final UserResource userResource = new UserResource(userDAO, accessTokenDAO, settingDAO);
		final NotificationsResource notificationsResource = new NotificationsResource(notificationsDAO);
		final SettingResource settingResource = new SettingResource(settingDAO);
		final SignatureResource signatureResource = new SignatureResource(signatureDAO, itemDAO);
		final DownloadResource downloadResource = new DownloadResource(configuration.getFilename());
		final ReportsResource reportsResource = new ReportsResource(itemDAO);
		
		// test resource
		final PingResource pingResource = new PingResource();
			
		// set jersey properties
		environment.jersey().setUrlPattern("/service/*");
		
		// add https forward
		if (configuration.isHttpsRedirect())
			addHttpsForward(environment.getApplicationContext());
		
		environment.getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		//environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				
		// register resources
		environment.jersey().register(MultiPart.class);
		environment.jersey().register(listResource);
		environment.jersey().register(itemResource);
		environment.jersey().register(itemsResource);
		environment.jersey().register(authResource);
		environment.jersey().register(userResource);
		environment.jersey().register(notificationsResource);
		environment.jersey().register(settingResource);
		environment.jersey().register(signatureResource);
		environment.jersey().register(downloadResource);
		environment.jersey().register(reportsResource);
		// test resource
		environment.jersey().register(pingResource);

		// register @RestrictedTo pre-matching
		environment.jersey().register(new RestrictedToMethodDispatchAdapter<Long>(
				new SimpleAuthenticator(hibernate.getSessionFactory()), configuration.getRealm()));
		
	}
}
