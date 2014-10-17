package com.nehp.rfid_system.server.auth;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.nehp.rfid_system.server.core.Authority;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;

class RestrictedToInjectable<T> extends AbstractHttpContextInjectable<T> {

	private static final Logger log = LoggerFactory
			.getLogger(RestrictedToInjectable.class);
	
	private static final String CHALLENGE_FORMAT = "Bearer realm=\"%s\"";
	private static final String PREFIX = "bearer";
	
	private final Authenticator<Credentials, T> authenticator;
	private final String realm;
	private final Set<Authority> requiredAuthorities;

	RestrictedToInjectable(Authenticator<Credentials, T> authenticator,
			String realm, Authority[] requiredAuthorities) {
		this.authenticator = authenticator;
		this.realm = realm;
		this.requiredAuthorities = Sets.newHashSet(Arrays
				.asList(requiredAuthorities));
	}

	public Authenticator<Credentials, T> getAuthenticator() {
		return authenticator;
	}

	public String getRealm() {
		return realm;
	}

	public Set<Authority> getRequiredAuthorities() {
		return requiredAuthorities;
	}

	@Override
	public T getValue(HttpContext httpContext) {
		log.warn("[RestrictedTo] Inside getValue");
		final String challenge = String.format(CHALLENGE_FORMAT, realm);
		try {			
			// Get the Authorization header
			final String header = httpContext.getRequest()
					.getHeaderValue(HttpHeaders.AUTHORIZATION);
			
			// "Bearer" & "UUID"
			final String[] tokens = header.split(" ");
			
			UUID sessionToken = null;
			
			if ( tokens.length != 2 ){
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}
			
			sessionToken = UUID.fromString(tokens[1]);
			
			final Credentials credentials = new Credentials(sessionToken,
					requiredAuthorities);
			
			System.out.println(header);
			System.out.println(tokens.toString());
			
            final Optional<T> result = authenticator.authenticate(credentials);
            if (result.isPresent() && result.get() != null) {
                return result.get();
            }

			
		} catch (IllegalArgumentException e) {
			log.debug("Error decoding credentials", e);
		} catch (AuthenticationException e) {
			log.warn("Error authenticating credentials", e);
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		// Must have failed to be here
		throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE,
                        challenge)
                .entity("Credentials are required to access this resource.")
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build());
	}

}
