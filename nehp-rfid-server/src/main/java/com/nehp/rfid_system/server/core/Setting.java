package com.nehp.rfid_system.server.core;

import static javax.persistence.GenerationType.IDENTITY;
import io.dropwizard.jackson.JsonSnakeCase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonSnakeCase
@Entity
@Table(name = "setting")
@JsonRootName(value = "setting")
@NamedQueries({
	@NamedQuery(name = "setting.getAll", query = "FROM Setting"),
	@NamedQuery(name = "setting.getByUser", query = "FROM Setting s WHERE s.user = :user")
})
public class Setting {

	public Setting(){}
	
	// INITIALIZERS
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@JsonProperty
	@Column( name = "id", nullable = false)
	private Long id;
	
	@JsonProperty
	@Column( name = "user", nullable = false)
	private Long user;
	
	@JsonProperty
	@Column( name = "user_changed", nullable = false)
	private Boolean user_changed;
	
	@JsonProperty
	@Column( name = "stage", nullable = false)
	private Integer stage;
	
	@JsonProperty
	@Column( name = "duration", nullable = false)
	private Integer duration;

	// SETTERS
	public void setId(long id){
		this.id = id;
	}
	public void setUser(long user){
		this.user = user;
	}
	public void setUserChanged(boolean changed){
		this.user_changed = changed;
	}
	public void setStage(Integer stage){
		this.stage = stage;
	}
	public void setDuration(Integer duration){
		this.duration = duration;
	}
	
	// GETTERS
	public Long getId(){
		return id;
	}
	public Long getUser(){
		return user;
	}
	public Boolean getUserChanged(){
		return user_changed;
	}
	public Integer getStage(){
		return stage;
	}
	public Integer getDuration(){
		return duration;
	}
}
