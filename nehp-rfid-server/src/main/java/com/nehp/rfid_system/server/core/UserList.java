package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

@JsonSnakeCase
public class UserList {

	@JsonProperty
	private List<User> users = Lists.newArrayList();
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> list){
		this.users = list;
	}
}
