package com.nehp.rfid_system.server;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.nehp.rfid_system.server.core.EmailCredentials;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class MainConfiguration extends Configuration {

	@NotEmpty
	private String realm;

	@Valid
	@JsonProperty
	private ImmutableList<String> allowedGrantTypes;

	@Valid
	@NotNull
	@JsonProperty("database")
	private final DataSourceFactory database = new DataSourceFactory();

	@Valid
	@NotNull
	@JsonProperty("httpsRedirect")
	private String httpsRedirect;

	@NotEmpty
	private String debug;

	@NotNull
	@JsonProperty("email")
	private EmailCredentials email = new EmailCredentials();

	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	@JsonProperty
	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getRealm() {
		return realm;
	}

	public ImmutableList<String> getAllowedGrantTypes() {
		return allowedGrantTypes;
	}

	public Boolean getDebug() {
		if (debug.equals("true"))
			return true;
		else
			return false;
	}

	public EmailCredentials getEmailCredentials() {
		return email;
	}

	public void setHttpsRedirect(Boolean set) {
		this.httpsRedirect = set.toString();
	}

	public boolean isHttpsRedirect() {
		if (httpsRedirect.equals("true"))
			return true;
		else
			return false;
	}

}
