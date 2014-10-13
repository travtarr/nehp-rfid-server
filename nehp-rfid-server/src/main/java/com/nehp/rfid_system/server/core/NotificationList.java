package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

@JsonSnakeCase
public class NotificationList {

	@JsonProperty
	private List<Notification> notifications = Lists.newArrayList();
	
	public List<Notification> getNotifications() {
		return notifications;
	}
	
	public void setNotifications(List<Notification> list){
		this.notifications = list;
	}
}
