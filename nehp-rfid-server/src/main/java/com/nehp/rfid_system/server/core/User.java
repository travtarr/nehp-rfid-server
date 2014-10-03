package com.nehp.rfid_system.server.core;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name = "users.getAll", query = "FROM User"),
	@NamedQuery(name = "users.getById", query = "FROM User WHERE id = :id"),
	@NamedQuery(name = "users.getByUsernameAndPassword", query = "FROM User WHERE username = :username AND password= :password"),
	@NamedQuery(name = "users.updateByUserId", query = "UPDATE User SET name= :name, "
			+ "email= :email, password= :password, lastLoginDate= :lastLoginDate WHERE id = :userId"),
	@NamedQuery(name = "users.deleteByUserId", query = "DELETE FROM User WHERE id = :userId")
})
public class User {
	
	public User(){}
	
	// INITIALIZERS
	@JsonProperty
	private long id;
	@JsonProperty
	private String username;
	@JsonProperty
	private String name;
	@JsonProperty
	private String email;
	@JsonProperty
	private String password;
	@JsonProperty
	private DateTime lastLoginDate;
	@JsonProperty
	private DateTime userCreatedDate;
	@JsonProperty
	private boolean admin;
	
	// SETTERS
	@JsonProperty
	public void setId(long id){
		this.id = id;
	}
	@JsonProperty
	public void setUsername(String username){
		this.username = username;
	}
	@JsonProperty
	public void setName(String name){
		this.name = name;
	}
	@JsonProperty
	public void setEmail(String email){
		this.email = email;
	}
	@JsonProperty
	public void setPassword(String password){
		this.password = password;
	}
	@JsonProperty
	public void setLastLoginDate(DateTime date){
		this.lastLoginDate = date;
	}
	@JsonProperty
	public void setUserCreatedDate(DateTime date){
		this.userCreatedDate = date;
	}
	@JsonProperty
	public void setAdmin(boolean admin){
		this.admin = admin;
	}
	
	// GETTERS
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty
	public long getId(){
		return id;
	}
	
	@Column(name = "username", unique = true, nullable = false, length = 32)
	@JsonProperty
	public String getUsername(){
		return username;
	}
	
	@Column(name = "name", nullable = false, length = 32)
	@JsonProperty
	public String getName(){
		return name;
	}
	
	@Column(name = "email", nullable = false, length = 64)
	@JsonProperty
	public String getEmail(){
		return email;
	}
	
	@Column(name = "password", nullable = false, length = 128)
	@JsonProperty
	public String getPassword(){
		return password;
	}
	
	@Column(name = "lastLoginDate", nullable = false)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getLastLoginDate(){
		return lastLoginDate;
	}
	
	@Column(name = "userCreatedDate", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getUserCreatedDate(){
		return userCreatedDate;
	}
	@Column(name = "admin", nullable = false)
	@JsonProperty
	public boolean getAdmin(){
		return admin;
	}
}
