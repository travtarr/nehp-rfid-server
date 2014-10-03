package com.nehp.rfid_system.server.data;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.UUID;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.AccessToken;

public class AccessTokenDAO extends AbstractDAO<AccessToken>{
	
	public AccessTokenDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}


	public Optional<AccessToken> findAccessTokenById(final UUID accessTokenId) {
		AccessToken accessToken = get(accessTokenId);

		if (accessToken == null)
			return Optional.absent();
		
		return Optional.of(accessToken);
	}

	public AccessToken generateNewAccessToken(final long userId, final DateTime dateTime) {
		AccessToken accessToken = new AccessToken(UUID.randomUUID(), userId, dateTime);
		persist(accessToken);
		return accessToken;
	}

	public void setLastAccessTime(final UUID accessTokenUUID, final DateTime dateTime) {
		AccessToken accessToken = get(accessTokenUUID);
		AccessToken updatedAccessToken = accessToken.withLastAccessUTC(dateTime);
		persist(updatedAccessToken);
	}
}