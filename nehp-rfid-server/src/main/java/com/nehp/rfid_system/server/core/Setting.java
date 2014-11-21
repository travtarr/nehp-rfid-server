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
	private long id;
	
	@JsonProperty
	@Column( name = "user", nullable = false)
	private long user;
	
	@JsonProperty
	@Column( name = "user_changed", nullable = false)
	private boolean user_changed;
	
	@JsonProperty
	@Column( name = "stage1", nullable = false)
	private String stage1;
	
	@JsonProperty
	@Column( name = "stage2", nullable = false)
	private String stage2;
	
	@JsonProperty
	@Column( name = "stage3", nullable = false)
	private String stage3;
	
	@JsonProperty
	@Column( name = "stage4", nullable = false)
	private String stage4;
	
	@JsonProperty
	@Column( name = "stage5", nullable = false)
	private String stage5;
	
	@JsonProperty
	@Column( name = "stage6", nullable = false)
	private String stage6;
	
	@JsonProperty
	@Column( name = "stage7", nullable = false)
	private String stage7;
	
	@JsonProperty
	@Column( name = "stage0", nullable = false)
	private String stage0;
	
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
	public void setStage1(String stage){
		this.stage1 = stage;
	}
	public void setStage2(String stage){
		this.stage2 = stage;
	}
	public void setStage3(String stage){
		this.stage3 = stage;
	}
	public void setStage4(String stage){
		this.stage4 = stage;
	}
	public void setStage5(String stage){
		this.stage5 = stage;
	}
	public void setStage6(String stage){
		this.stage6 = stage;
	}
	public void setStage7(String stage){
		this.stage7 = stage;
	}
	public void setStage0(String stage){
		this.stage0 = stage;
	}
	
	// GETTERS
	public long getId(){
		return id;
	}
	public long getUser(){
		return user;
	}
	public boolean getUserChanged(){
		return user_changed;
	}
	public String getStage1(){
		return stage1;
	}
	public String getStage2(){
		return stage2;
	}
	public String getStage3(){
		return stage3;
	}
	public String getStage4(){
		return stage4;
	}
	public String getStage5(){
		return stage5;
	}
	public String getStage6(){
		return stage6;
	}
	public String getStage7(){
		return stage7;
	}
	public String getStage0(){
		return stage0;
	}
}
