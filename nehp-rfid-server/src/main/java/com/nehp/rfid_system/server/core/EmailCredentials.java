package com.nehp.rfid_system.server.core;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailCredentials extends Object {

	@NotEmpty
	@JsonProperty
	private String hostName;
	
	@JsonProperty
	private int hostPort = 465;
	
	@JsonProperty
	private boolean ssl = true;
	
	@NotEmpty
	@JsonProperty
	private String emailAddress;
	
	@NotEmpty
	@JsonProperty
	private String emailUser;
	
	@NotEmpty
	@JsonProperty
	private String emailPassword;
	
	public EmailCredentials(){}
	
	public void setHostName (String hostName){
		this.hostName = hostName;
	}
	
	public void setHostPort (int hostPort){
		this.hostPort = hostPort;
	}
	
	public void setSSL (boolean ssl){
		this.ssl = ssl;
	}
	
	public void setEmailAddress (String emailAddress){
		this.emailAddress = emailAddress;
	}
	
	public void setEmailUser (String emailUser){
		this.emailUser = emailUser;
	}
	
	public void setEmailPassword (String emailPassword){
		this.emailPassword = emailPassword;
	}
	
	public String getHostName (){
		return hostName;
	}
	
	public int getHostPort (){
		return hostPort;
	}
	
	public boolean getSSL (){
		return ssl;
	}
	
	public String getEmailAddress (){
		return emailAddress;
	}
	
	public String getEmailUser (){
		return emailUser;
	}
	
	public String getEmailPassword (){
		return emailPassword;
	}
}
