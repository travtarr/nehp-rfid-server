package com.nehp.rfid_system.server.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
public class ItemMinList {
	
	@JsonProperty("itemmins")
	private List<ItemMin> itemMins = Lists.newArrayList();
	
	public List<ItemMin> getItemMins(){
		return itemMins;
	}
	
	public void setItemMins(List<ItemMin> list){
		this.itemMins = list;
	}
}
