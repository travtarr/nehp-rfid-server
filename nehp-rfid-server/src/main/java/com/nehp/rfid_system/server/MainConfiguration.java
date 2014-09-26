package com.nehp.rfid_system.server;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class MainConfiguration extends Configuration {

	@NotEmpty
	private String template;

	@NotEmpty
	private String defaultName = "Stranger";
	
	@NotEmpty
	private String realm;

	@Valid
	@JsonProperty
	private ImmutableList<String> allowedGrantTypes;
	
	@Valid
	@NotNull
	@JsonProperty("database")
	private final DataSourceFactory database= new DataSourceFactory();
	
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }	
	
	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	@JsonProperty
	public void setDefaultName(String name) {
		this.defaultName = name;
	}
	
	@JsonProperty
	public void setRealm(String realm){
		this.realm = realm;
	}
	
	@JsonProperty
	public String getRealm(){
		return realm;
	}
	
	@JsonProperty
	public ImmutableList<String> getAllowedGrantTypes(){
		return allowedGrantTypes;
	}
}
