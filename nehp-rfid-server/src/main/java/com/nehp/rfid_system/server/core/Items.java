package com.nehp.rfid_system.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@JsonRootName("items")
public class Items {

	@JsonProperty
	private long id;
	
	@JsonProperty
	public long getID(){
		return id;
	}
	
	@JsonProperty
	public void setID(Long id){
		this.id = id;
	}
}
