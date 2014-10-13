package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonSnakeCase
@JsonRootName("user")
public class UserWrap {
	
	@JsonProperty
	private User user = new User();
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
}
