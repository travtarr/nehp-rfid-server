package com.nehp.rfid_system.server.auth;

import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.data.AccessTokenDAO;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class SimpleAuthenticator implements Authenticator<String, Long>{
	public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 10;
	private AccessTokenDAO accessTokenDAO;
	
	public SimpleAuthenticator(AccessTokenDAO accessTokenDAO) {
		super();
	}

	@Override
	public Optional<Long> authenticate(String accessTokenId)
			throws AuthenticationException {
		UUID accessTokenUUID;
		
		// Check for valid UUID
		try {
			accessTokenUUID = UUID.fromString(accessTokenId);
		} catch (IllegalArgumentException e) {
			return Optional.absent();
		}
		
		// Check if in DB
		Optional<AccessToken> accessToken = accessTokenDAO.findAccessTokenById(accessTokenUUID);
		if (accessToken == null || !accessToken.isPresent()){
			return Optional.absent();
		}
		
		// Check if access time is within expiration time
		Period period = new Period(accessToken.get().getLastAccessUTC(), new DateTime());
		if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN){
			return Optional.absent();
		}
		
		// Update the access time for the token
		accessTokenDAO.setLastAccessTime(accessTokenUUID, new DateTime());
		
		// Return the user's id for processing
		return Optional.of(accessToken.get().getUserId());
	}

}
