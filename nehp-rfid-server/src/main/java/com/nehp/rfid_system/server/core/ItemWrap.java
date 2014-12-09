package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonSnakeCase
@JsonRootName("item")
public class ItemWrap {
	
	@JsonProperty
	private Item item = new Item();
	
	public Item getItem(){
		return item;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
}
