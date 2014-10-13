package com.nehp.rfid_system.server.auth;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.core.User;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class SimpleAuthenticator implements Authenticator<Credentials, Long>{
	public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 10;
	private SessionFactory sessionFactory;
	
	public SimpleAuthenticator(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Optional<Long> authenticate(Credentials credentials)
			throws AuthenticationException {
		UUID accessTokenUUID;
		
		// Check for valid UUID
		try {
			accessTokenUUID = credentials.getSessionToken();
		} catch (IllegalArgumentException e) {
			return Optional.absent();
		}
		
		// Check if UUID was created
		if(accessTokenUUID == null)
			return Optional.absent();
		
		// Check if in DB
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		AccessToken accessToken =  (AccessToken) session.get(AccessToken.class, accessTokenUUID);
		if(accessToken == null){
			session.getTransaction().commit();
			session.close();
			return Optional.absent();
		}
		Optional<AccessToken> accessTokenOpt =  Optional.of(accessToken);
		
		// check if user has the correct authorities
		User user = (User) session.get(User.class, accessToken.getUserId());
		if(!user.hasAllAuthorities(credentials.getRequiredAuthorities())) {
			return Optional.absent();
		}
		
		// Check if access time is within expiration time
		Period period = new Period(accessTokenOpt.get().getLastAccess(), new DateTime());
		if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN){
			// TODO: delete expired accessToken
			return Optional.absent();
		}
		
		// Update the access time for the token
		AccessToken updatedAccessToken = accessTokenOpt.get().withLastAccessUTC(new DateTime());
		session.saveOrUpdate(updatedAccessToken);
		session.getTransaction().commit();
		session.close();
		
		// Return the user's id for processing
		return Optional.of(accessTokenOpt.get().getUserId());
	}

}
