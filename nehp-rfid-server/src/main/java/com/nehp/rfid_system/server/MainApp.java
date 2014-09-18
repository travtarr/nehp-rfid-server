package com.nehp.rfid_system.server;

import com.nehp.rfid_system.server.health.TemplateHealthCheck;
import com.nehp.rfid_system.server.resources.HelloWorldResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MainApp extends Application<MainConfiguration> {

	public static void main(String[] args) throws Exception {
		new MainApp().run(args);
	}

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<MainConfiguration> bootstrap) {
		// nothing to do yet
	}

	@Override
	public void run(MainConfiguration configuration,
			Environment environment) {
		
		final HelloWorldResource resource = new HelloWorldResource(
				configuration.getTemplate(), configuration.getDefaultName());
		
		final TemplateHealthCheck healthCheck =
		        new TemplateHealthCheck(configuration.getTemplate());
		    environment.healthChecks().register("template", healthCheck);
		    
		environment.jersey().register(resource);
	}

}
