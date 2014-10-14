package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

@JsonSnakeCase
public class ItemList {

	@JsonProperty
	private List<Item> items = Lists.newArrayList();
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> list){
		this.items = list;
	}
}
