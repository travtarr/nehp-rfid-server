package com.nehp.rfid_system.server.core;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@JsonProperty
	@Column( name = "id", nullable = false)
	private long id;
	
	@JsonProperty
	@Column( name = "title", nullable = false)
	private String title;
	
	@JsonProperty
	@Column( name = "message", nullable = true)
	private String message;
	
	@JsonProperty
	@Column( name = "date", nullable = true)
	private Date date;
	
	@JsonProperty
	@Column( name = "created_by", nullable = false)
	private String created_by;
	
	// SETTERS
	public void setId(long id){
		this.id = id;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void setCreatedBy(String createdBy){
		this.created_by = createdBy;
	}
	
	// GETTERS
	public long getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getMessage(){
		return message;
	}
	
	public Date getDate(){
		return date;
	}
	
	public String getCreatedBy(){
		return created_by;
	}
}
