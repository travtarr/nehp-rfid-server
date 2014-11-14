package com.nehp.rfid_system.server.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.spi.container.ResourceMethodDispatchAdapter;
import com.sun.jersey.spi.container.ResourceMethodDispatchProvider;
import com.sun.jersey.spi.dispatch.RequestDispatcher;

@Provider
public class RestrictedToMethodDispatchAdapter<T> implements
		ResourceMethodDispatchAdapter {

	private final Authenticator<Credentials, T> authenticator;
	private final String realm;

	public RestrictedToMethodDispatchAdapter(
			Authenticator<Credentials, T> authenticator, String realm) {
		this.authenticator = authenticator;
		this.realm = realm;
	}

	@Override
	public ResourceMethodDispatchProvider adapt(
			ResourceMethodDispatchProvider provider) {
		return new RestrictedToMethodDispatchProvider<T>(provider,
				authenticator, realm);
	}

	private static class RestrictedToMethodDispatchProvider<T> implements
			ResourceMethodDispatchProvider {

		private final ResourceMethodDispatchProvider provider;
		private final Authenticator<Credentials, T> authenticator;
		private final String realm;

		public RestrictedToMethodDispatchProvider(
				ResourceMethodDispatchProvider provider,
				Authenticator<Credentials, T> authenticator, String realm) {
			this.provider = provider;
			this.authenticator = authenticator;
			this.realm = realm;
			
		}

		@Override
		public RequestDispatcher create(AbstractResourceMethod method) {
			RequestDispatcher dispatcher = provider.create(method);

			if (dispatcher == null) {
				return null;
			} else if (method.getMethod().isAnnotationPresent(
					RestrictedTo.class)) {
				// evaluate the annotation for permissions here
				dispatcher = new RestrictedToRequestDispatcher<T>(dispatcher,
						method.getMethod().getAnnotation(RestrictedTo.class)
								.value(), authenticator, realm);
			}

			return dispatcher;
		}
	}

	private static class RestrictedToRequestDispatcher<T> implements
			RequestDispatcher {

		private final RequestDispatcher underlying;
		private final Set<Authority> requiredAuthorities;
		private final Authenticator<Credentials, T> authenticator;
		private final String realm;
		
		private static final String CHALLENGE_FORMAT = "Bearer realm=\"%s\"";

		public RestrictedToRequestDispatcher(RequestDispatcher underlying,
				Authority[] authority, Authenticator<Credentials, T> authenticator, String realm) {
			this.underlying = underlying;
			this.authenticator = authenticator;
			this.requiredAuthorities = Sets
					.newHashSet(Arrays.asList(authority));
			this.realm = realm;
		}

		@Override
		public void dispatch(Object resource, HttpContext context) {
			
			try {
				// Get the Authorization header
				final String header = context.getRequest().getHeaderValue(
						HttpHeaders.AUTHORIZATION);
				
				if(header == null || header.isEmpty())
					throwUnauthorized();
				
				// "Bearer" & "UUID"
				final String[] tokens = header.split(" ");

				UUID sessionToken = null;

				//System.out.println("[Token array] " + tokens[0] + " , " + tokens[1]);
				
				if (tokens.length != 2) {
					throwUnauthorized();
				}

				sessionToken = UUID.fromString(tokens[1]);

				final Credentials credentials = new Credentials(sessionToken,
						requiredAuthorities);
				System.out.println(credentials.getSessionToken());
				System.out.println(credentials.getRequiredAuthorities());

				final Optional<T> result = authenticator
						.authenticate(credentials);
				
				if (result.isPresent()) {
					//System.out.println("[RestrictedTo] dispatches successfully");
					underlying.dispatch(resource, context);
				} else {
					System.out.println("Doesn't get credentials authenticated");
					// Must have failed to be here
					throwUnauthorized();
				}

			} catch (IllegalArgumentException e) {
				throwUnauthorized();
			} catch (AuthenticationException e) {
				throw new WebApplicationException(
						Response.Status.INTERNAL_SERVER_ERROR);
			}
			
			
		}
		
		private void throwUnauthorized(){
			final String challenge = String.format(CHALLENGE_FORMAT, realm);
			
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
	                .header(HttpHeaders.WWW_AUTHENTICATE,
	                        challenge)
	                .entity("Credentials are required to access this resource.")
	                .type(MediaType.TEXT_PLAIN_TYPE)
	                .build());
		}
	}
}
