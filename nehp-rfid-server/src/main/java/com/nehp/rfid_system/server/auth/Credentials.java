package com.nehp.rfid_system.server.auth;

import java.util.Set;
import java.util.UUID;

import com.google.common.base.Objects;
import com.nehp.rfid_system.server.core.Authority;

import static com.google.common.base.Preconditions.checkNotNull;

public class Credentials {

	private final UUID sessionToken;
	private final Set<Authority> requiredAuthorities;

	public Credentials(UUID sessionToken, Set<Authority> requiredAuthorities) {
		this.sessionToken = checkNotNull(sessionToken);
		this.requiredAuthorities = checkNotNull(requiredAuthorities);
	}

	public UUID getSessionToken() {
		return sessionToken;
	}

	public Set<Authority> getRequiredAuthorities() {
		return requiredAuthorities;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final Credentials that = (Credentials) obj;

		return sessionToken.equals(that.sessionToken);
	}

	@Override
	public int hashCode() {
		return (31 * sessionToken.hashCode());
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("sessionId", sessionToken)
				.add("authorities", requiredAuthorities).toString();
	}
}
