package com.nehp.rfid_system.server.core;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	
	public AccessToken(UUID id, long userId, Date lastAccess){
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
	private Date lastAccess;

	public UUID getId(){
		return id;
	}

	public long getUserId(){
		return userId;
	}

	public Date getLastAccess(){
		return lastAccess;
	}
	
	public void setUuid(UUID id){
		this.id = id;
	}

	public void setUserId(long userId){
		this.userId = userId;
	}
	
	public void setLastAccessUTC(Date lastAccess){
		this.lastAccess = lastAccess;
	}
	
	public AccessToken withLastAccessUTC(Date lastAccess){
		this.lastAccess = lastAccess;
		return this;
	}
}
