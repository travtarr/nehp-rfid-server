package com.nehp.rfid_system.server.core;


import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
//import org.joda.time.contrib.hibernate.PersistentDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "accessToken")
@NamedQueries({
	@NamedQuery(name = "accessTokens.getById", 
			query = "FROM AccessToken p WHERE p.id = :accessTokenId")
})
public class AccessToken {
	
	public AccessToken(){}
	
	public AccessToken(UUID id, long userId, DateTime lastAccess){
		this.id = id;
		this.userId = userId;
		this.lastAccess = lastAccess;
	}
	
	@Id
	@JsonProperty
	@NotNull
	@Column(name = "id")
	private UUID id;
	
	@JsonProperty
	@NotNull
	@Column(name = "userid")
	private long userId;
	
	@JsonProperty
	@NotNull
	@Column(name = "last_access")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lastAccess;
	
	@JsonProperty
	public UUID getId(){
		return id;
	}
		
	@JsonProperty
	public long getUserId(){
		return userId;
	}
	
	@JsonProperty
	public DateTime getLastAccess(){
		return lastAccess;
	}
	
	@JsonProperty
	public void setUuid(UUID id){
		this.id = id;
	}
	
	@JsonProperty
	public void setUserId(long userId){
		this.userId = userId;
	}
	
	@JsonProperty
	public void setLastAccessUTC(DateTime lastAccess){
		this.lastAccess = lastAccess;
	}
	
	@JsonProperty
	public AccessToken withLastAccessUTC(DateTime lastAccess){
		this.lastAccess = lastAccess;
		return this;
	}
}