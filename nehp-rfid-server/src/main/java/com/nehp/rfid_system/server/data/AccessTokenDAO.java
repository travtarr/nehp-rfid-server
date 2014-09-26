package com.nehp.rfid_system.server.data;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.core.User;

public class AccessTokenDAO extends AbstractDAO<AccessToken>{
	
	public AccessTokenDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	private static Map<UUID, AccessToken> accessTokenTable = new HashMap<>();

	public Optional<AccessToken> findAccessTokenById(final UUID accessTokenId) {
		AccessToken accessToken = accessTokenTable.get(accessTokenId);
		if (accessToken == null) {
			return Optional.absent();
		}
		return Optional.of(accessToken);
	}

	public AccessToken generateNewAccessToken(final User user, final DateTime dateTime) {
		AccessToken accessToken = new AccessToken(UUID.randomUUID(), user.getId(), dateTime);
		accessTokenTable.put(accessToken.getId(), accessToken);
		return accessToken;
	}

	public void setLastAccessTime(final UUID accessTokenUUID, final DateTime dateTime) {
		AccessToken accessToken = accessTokenTable.get(accessTokenUUID);
		AccessToken updatedAccessToken = accessToken.withLastAccessUTC(dateTime);
		accessTokenTable.put(accessTokenUUID, updatedAccessToken);
	}
}