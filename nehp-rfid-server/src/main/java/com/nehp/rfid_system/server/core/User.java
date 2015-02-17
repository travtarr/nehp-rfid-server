package com.nehp.rfid_system.server.core;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
	@NamedQuery(name = "users.getByEmail", query = "FROM User WHERE email = :email"),
	@NamedQuery(name = "users.updateByUserId", query = "UPDATE User SET name= :name, "
			+ "email= :email, password= :password, last_login_date= :last_login_date WHERE id = :userId"),
	@NamedQuery(name = "users.deleteByUserId", query = "DELETE FROM User WHERE id = :userId")
})
public class User {
	
	public User(){}
	
	// INITIALIZERS
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty
	private long id;
	
	@Column(name = "username", unique = true, nullable = false, length = 32)
	@JsonProperty
	private String username;
	
	@Column(name = "name", nullable = false, length = 32)
	@JsonProperty
	private String name;
	
	@Column(name = "email", nullable = false, length = 64)
	@JsonProperty
	private String email;
	
	@Column(name = "setting")
	@JsonProperty
	private long setting;
	
	@Column(name = "password", nullable = false, length = 89)
	@JsonProperty
	private String password;
	
	@Column(name = "password_reset", nullable = true)
	@JsonProperty("password_reset")
	private boolean password_reset;
	
	@Column(name = "last_login_date", nullable = true)
	@JsonProperty("last_login_date")
	private Date last_login_date;
	
	@Column(name = "user_created_date", nullable = true)
	@JsonProperty("user_created_date")
	private Date user_created_date;
	
	@Column(name = "admin", nullable = false)
	@JsonProperty
	private boolean admin;
	
	@Column(name = "scanner", nullable = true)
	@JsonProperty
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
	public void setSetting(long setting){
		this.setting = setting;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setPasswordReset(boolean reset){
		this.password_reset = reset;
	}
	public void setLastLoginDate(Date date){
		this.last_login_date = date;
	}
	public void setUserCreatedDate(Date date){
		this.user_created_date = date;
	}
	public void setAdmin(boolean admin){
		this.admin = admin;
	}
	public void setScanner(boolean scanner){
		this.scanner = scanner;
	}
	
	// GETTERS
	
	public long getId(){
		return id;
	}

	public String getUsername(){
		return username;
	}

	public String getName(){
		return name;
	}

	public String getEmail(){
		return email;
	}
	
	public long getSetting(){
		return setting;
	}

	public String getPassword(){
		return password;
	}
	
	@JsonProperty("password_reset")
	public boolean getPasswordReset(){
		return password_reset;
	}

	@JsonProperty("last_login_date")
	public Date getLastLoginDate(){
		return last_login_date;
	}

	@JsonProperty("user_created_date")
	public Date getUserCreatedDate(){
		return user_created_date;
	}

	public boolean getAdmin(){
		return admin;
	}

	public boolean getScanner(){
		return scanner;
	}
	
	@JsonProperty
	public boolean hasAllAuthorities(Set<Authority> requiredAuthorities){
		Set<Authority> authorities = Sets.newHashSet();
		if(admin)
			authorities.add(Authority.ROLE_ADMIN);
		if(scanner)
			authorities.add(Authority.ROLE_SCANNER);
		
		authorities.add(Authority.ROLE_USER);
		
		return authorities.containsAll(requiredAuthorities);
	}
	
	@JsonProperty
	public boolean hasAuthority(Authority authority){
		return hasAllAuthorities(Sets.newHashSet(authority));
	}
	
	/*@Override
	public String toString(){
		return String.format("User [id=%s, username=%s, name=%s, email=%s, "
				+ "setting=%s, password=%s, password_reset=%s, last_login_date=%s, "
				+ "user_created_date=%s, admin=%s, scanner=%s]", id, username, name, email, setting,
				password, password_reset, last_login_date, user_created_date, admin, scanner);
	}*/
}
