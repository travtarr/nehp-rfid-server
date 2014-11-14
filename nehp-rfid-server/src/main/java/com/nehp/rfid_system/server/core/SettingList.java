package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

@JsonSnakeCase
public class SettingList {
	@JsonProperty
	private List<Setting> settings = Lists.newArrayList();
	
	public List<Setting> getSettings() {
		return settings;
	}
	
	public void setSettings(List<Setting> list){
		this.settings = list;
	}
}
