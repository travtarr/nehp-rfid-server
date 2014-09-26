package com.nehp.rfid_system.server.core;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name = "users.getAll", query = "FROM Users"),
	@NamedQuery(name = "users.getById", query = "FROM Users WHERE id = :id"),
	@NamedQuery(name = "users.getByUsernameAndPassword", query = "FROM Users WHERE userId = :username AND password= :password"),
	@NamedQuery(name = "users.updateByUserId", query = "UPDATE Users SET name= :name, "
			+ "email= :email, password= :password, p.lastLoginDate= :lastLoginDate WHERE userId = :userId"),
	@NamedQuery(name = "users.deleteByUserId", query = "DELETE FROM Users WHERE userId = :userId")
})
public class User {
	
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
	private String lastLoginDate;
	@JsonProperty
	private String userCreatedDate;
	
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
	public void setLastLoginDate(String date){
		this.lastLoginDate = date;
	}
	@JsonProperty
	public void setuserCreatedDate(String date){
		this.userCreatedDate = date;
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
	
	@Column(name = "lastLoginDate", nullable = false, length = 24)
	@JsonProperty
	public String getLastLoginDate(){
		return lastLoginDate;
	}
	
	@Column(name = "userCreatedDate", nullable = false, length = 24)
	@JsonProperty
	public String getUserCreatedDate(){
		return userCreatedDate;
	}
}
