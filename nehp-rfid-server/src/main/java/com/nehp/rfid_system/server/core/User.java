package com.nehp.rfid_system.server.core;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

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
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Sets;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "users")
@JsonRootName(value = "user")
@NamedQueries({
	@NamedQuery(name = "users.getAll", query = "FROM User"),
	@NamedQuery(name = "users.getByUsername", query = "FROM User WHERE username = :username"),
	@NamedQuery(name = "users.updateByUserId", query = "UPDATE User SET name= :name, "
			+ "email= :email, password= :password, lastLoginDate= :lastLoginDate WHERE id = :userId"),
	@NamedQuery(name = "users.deleteByUserId", query = "DELETE FROM User WHERE id = :userId")
})
public class User {
	
	public User(){}
	
	// INITIALIZERS
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;
	
	@Column(name = "username", unique = true, nullable = false, length = 32)
	private String username;
	
	@Column(name = "name", nullable = false, length = 32)
	private String name;
	
	@Column(name = "email", nullable = false, length = 64)
	private String email;
	
	@Column(name = "password", nullable = false, length = 89)
	private String password;
	
	@Column(name = "last_login_date", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lastLoginDate;
	
	@Column(name = "user_created_date", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime userCreatedDate;
	
	@Column(name = "admin", nullable = false)
	private boolean admin;
	
	@Column(name = "scanner", nullable = true)
	private boolean scanner;
	
	// SETTERS
	public void setId(long id){
		this.id = id;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setLastLoginDate(DateTime date){
		this.lastLoginDate = date;
	}
	public void setUserCreatedDate(DateTime date){
		this.userCreatedDate = date;
	}
	public void setAdmin(boolean admin){
		this.admin = admin;
	}
	public void setScanner(boolean scanner){
		this.scanner = scanner;
	}
	
	// GETTERS
	@JsonProperty
	public long getId(){
		return id;
	}
	@JsonProperty
	public String getUsername(){
		return username;
	}
	@JsonProperty
	public String getName(){
		return name;
	}
	@JsonProperty
	public String getEmail(){
		return email;
	}
	@JsonProperty
	public String getPassword(){
		return password;
	}
	@JsonProperty
	public DateTime getLastLoginDate(){
		return lastLoginDate;
	}
	@JsonProperty
	public DateTime getUserCreatedDate(){
		return userCreatedDate;
	}
	@JsonProperty
	public boolean getAdmin(){
		return admin;
	}
	@JsonProperty
	public boolean getScanner(){
		return scanner;
	}
	
	public boolean hasAllAuthorities(Set<Authority> requiredAuthorities){
		Set<Authority> authorities = Sets.newHashSet();
		if(admin)
			authorities.add(Authority.ROLE_ADMIN);
		if(scanner)
			authorities.add(Authority.ROLE_SCANNER);
		
		authorities.add(Authority.ROLE_USER);
		
		return authorities.containsAll(requiredAuthorities);
	}
	
	public boolean hasAuthority(Authority authority){
		return hasAllAuthorities(Sets.newHashSet(authority));
	}
}
