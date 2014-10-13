package com.nehp.rfid_system.server.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "notifications")
@JsonRootName(value = "notification")
@NamedQuery(name = "notifications.getAll", query = "FROM Notification")
public class Notification {

	public Notification(){}
	
	// INITIALIZERS
	@JsonProperty
	private long id;
	@JsonProperty
	private String title;
	@JsonProperty
	private String message;
	@JsonProperty
	private DateTime date;
	@JsonProperty
	private String created_by;
	
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
	@JsonProperty
	public void setCreatedBy(String createdBy){
		this.created_by = createdBy;
	}
	
	// GETTERS
	@Id
	@JsonProperty
	@Column( name = "id", nullable = false)
	public long getId(){
		return id;
	}
	@JsonProperty
	@Column( name = "title", nullable = false)
	public String getTitle(){
		return title;
	}
	@JsonProperty
	@Column( name = "message", nullable = true)
	public String getMessage(){
		return message;
	}
	@JsonProperty
	@Column( name = "date", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getDate(){
		return date;
	}
	@JsonProperty
	@Column( name = "created_by", nullable = false)
	public String getCreatedBy(){
		return created_by;
	}
}
