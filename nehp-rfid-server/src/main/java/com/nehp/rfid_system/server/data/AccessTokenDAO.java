package com.nehp.rfid_system.server.data;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.Date;
import java.util.UUID;

import org.hibernate.SessionFactory;
import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.AccessToken;

public class AccessTokenDAO extends AbstractDAO<AccessToken>{
	
	public AccessTokenDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Get accessToken by its unique ID.
	 * 
	 * @param accessTokenId
	 * @return
	 */
	public Optional<AccessToken> findAccessTokenById(final UUID accessTokenId) {
		AccessToken accessToken = get(accessTokenId);

		if (accessToken == null)
			return Optional.absent();
		
		return Optional.of(accessToken);
	}

	/**
	 * Create a new access token.
	 * 
	 * @param userId
	 * @param dateTime
	 * @return
	 */
	public AccessToken generateNewAccessToken(final long userId, final Date dateTime) {
		AccessToken accessToken = new AccessToken(UUID.randomUUID(), userId, dateTime);
		persist(accessToken);
		return accessToken;
	}

	/**
	 * Update last access time of token.
	 * 
	 * @param accessTokenUUID
	 * @param dateTime
	 */
	public void setLastAccessTime(final UUID accessTokenUUID, final Date dateTime) {
		AccessToken accessToken = get(accessTokenUUID);
		AccessToken updatedAccessToken = accessToken.withLastAccessUTC(dateTime);
		persist(updatedAccessToken);
	}
}