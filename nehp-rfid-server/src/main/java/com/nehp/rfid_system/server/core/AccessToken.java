package com.nehp.rfid_system.server.core;


import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "accessToken")
@NamedQueries({
	@NamedQuery(name = "accessTokens.getById", 
			query = "FROM AccessTokens p WHERE p.id = :accessTokenId")
})
public class AccessToken {
	
	public AccessToken(UUID id, long userId, DateTime lastAccess){
		this.accessTokenId = id;
		this.userId = userId;
		this.lastAccessUTC = lastAccess;
	}
	
	@JsonProperty
	@NotNull
	private UUID accessTokenId;
	
	@JsonProperty
	@NotNull
	private long userId;
	
	@JsonProperty
	@NotNull
	private DateTime lastAccessUTC;
	
	@JsonProperty
	public UUID getId(){
		return accessTokenId;
	}
	
	@JsonProperty
	public long getUserId(){
		return userId;
	}
	
	@JsonProperty
	public DateTime getLastAccessUTC(){
		return lastAccessUTC;
	}
	
	@JsonProperty
	public void setId(UUID id){
		this.accessTokenId = id;
	}
	
	@JsonProperty
	public void setUserId(long userId){
		this.userId = userId;
	}
	
	@JsonProperty
	public void setLastAccessUTC(DateTime lastAccess){
		this.lastAccessUTC = lastAccess;
	}
	
	@JsonProperty
	public AccessToken withLastAccessUTC(DateTime lastAccess){
		this.lastAccessUTC = lastAccess;
		return this;
	}
}
