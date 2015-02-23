package com.nehp.rfid_system.server.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
public class StageLogList {
	
	@JsonProperty
	private List<StageLog> stagelogs = Lists.newArrayList();
	
	public List<StageLog> getStageLogList() {
		return stagelogs;
	}
	
	public void setStageLog(List<StageLog> stagelogs) {
		this.stagelogs = stagelogs;
	}
}
