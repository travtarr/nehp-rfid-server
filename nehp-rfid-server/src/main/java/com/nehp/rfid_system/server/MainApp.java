package com.nehp.rfid_system.server;

import org.joda.time.DateTimeZone;
import org.skife.jdbi.v2.DBI;

import com.nehp.rfid_system.server.auth.SimpleAuthenticator;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.data.AccessTokenDAO;
import com.nehp.rfid_system.server.data.ItemDAO;
import com.nehp.rfid_system.server.data.UserDAO;
import com.nehp.rfid_system.server.health.TemplateHealthCheck;
import com.nehp.rfid_system.server.resources.AuthResource;
import com.nehp.rfid_system.server.resources.HelloWorldResource;
import com.nehp.rfid_system.server.resources.ItemResource;
import com.nehp.rfid_system.server.resources.ListResource;

import io.dropwizard.Application;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi.DBIFactory;
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
		DateTimeZone.setDefault(DateTimeZone.UTC);
	}

	@Override
	public void run(MainConfiguration configuration, Environment environment)
			throws ClassNotFoundException {
		// Create DAOs

		// jbdi
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment,
				configuration.getDataSourceFactory(), "mysql");

		// initialize resources
		final HelloWorldResource resource = new HelloWorldResource(
				configuration.getTemplate(), configuration.getDefaultName());
		final ListResource listResource = new ListResource(new ItemDAO(
				hibernate.getSessionFactory()));
		final ItemResource itemResource = new ItemResource(new ItemDAO(
				hibernate.getSessionFactory()));
		final AuthResource authResource = new AuthResource(
				configuration.getAllowedGrantTypes(), new AccessTokenDAO(
						hibernate.getSessionFactory()), new UserDAO(
						hibernate.getSessionFactory()));

		// initialize healthchecks
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(
				configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);

		// register resources
		environment.jersey().register(resource);
		environment.jersey().register(listResource);
		environment.jersey().register(itemResource);
		environment.jersey().register(authResource);

		// register auth provider
		environment.jersey().register(
				new OAuthProvider<>(new SimpleAuthenticator(new AccessTokenDAO(
						hibernate.getSessionFactory())), configuration
						.getRealm()));
	}

	private HibernateBundle<MainConfiguration> hibernate = new HibernateBundle<MainConfiguration>(
			User.class, Item.class) {
		public DataSourceFactory getDataSourceFactory(
				MainConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

}
