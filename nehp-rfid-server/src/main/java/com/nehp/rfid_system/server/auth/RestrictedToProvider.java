package com.nehp.rfid_system.server.auth;

import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;

import io.dropwizard.auth.Authenticator;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

public class RestrictedToProvider<T> implements InjectableProvider<RestrictedTo, Parameter> {

	private final Authenticator<Credentials, T> authenticator;
	private final String realm;
	
	public RestrictedToProvider(Authenticator<Credentials, T> authenticator, String realm) {
		this.authenticator = authenticator;
		this.realm = realm;
	}
	
	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}

	@Override
	public Injectable<?> getInjectable(ComponentContext ic, RestrictedTo a,
			Parameter c) {
		return new RestrictedToInjectable<T>(authenticator, realm, a.value());
	}
}
