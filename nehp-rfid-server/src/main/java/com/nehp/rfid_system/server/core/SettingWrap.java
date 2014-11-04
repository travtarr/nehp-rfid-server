package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonSnakeCase
@JsonRootName("setting")
public class SettingWrap {
	
	@JsonProperty
	private Setting setting = new Setting();
	
	public Setting getSetting(){
		return setting;
	}
	
	public void setSetting(Setting setting){
		this.setting = setting;
	}
}
