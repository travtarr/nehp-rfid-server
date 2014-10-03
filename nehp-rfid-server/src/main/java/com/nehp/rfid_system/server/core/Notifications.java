package com.nehp.rfid_system.server.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "notifications")
@NamedQuery(name = "notifications.getAll", query = "FROM Notifications")
public class Notifications {

	public Notifications(){}
	
	// INITIALIZERS
	@JsonProperty
	private long id;
	@JsonProperty
	private String title;
	@JsonProperty
	private String message;
	@JsonProperty
	private DateTime date;
	
	// SETTERS
	@JsonProperty
	public void setId(long id){
		this.id = id;
	}
	@JsonProperty
	public void setTitle(String title){
		this.title = title;
	}
	@JsonProperty
	public void setMessage(String message){
		this.message = message;
	}
	@JsonProperty
	public void setDate(DateTime date){
		this.date = date;
	}
	
	// GETTERS
	@Id
	@JsonProperty
	@Column
	public long getId(){
		return id;
	}
	@JsonProperty
	@Column
	public String getTitle(){
		return title;
	}
	@JsonProperty
	@Column
	public String getMessage(){
		return message;
	}
	@JsonProperty
	@Column
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getDate(){
		return date;
	}
}
