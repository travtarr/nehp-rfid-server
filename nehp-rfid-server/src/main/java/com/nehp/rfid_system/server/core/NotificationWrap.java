package com.nehp.rfid_system.server.core;

import io.dropwizard.jackson.JsonSnakeCase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonSnakeCase
@JsonRootName("notification")
public class NotificationWrap {
	
	@JsonProperty
	private Notification Notification = new Notification();
	
	public Notification getNotification(){
		return Notification;
	}
	
	public void setNotification(Notification Notification){
		this.Notification = Notification;
	}
}
