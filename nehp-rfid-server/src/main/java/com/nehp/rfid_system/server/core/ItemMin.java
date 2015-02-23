package com.nehp.rfid_system.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@JsonRootName("itemmin")
public class ItemMin {
	
	public ItemMin(){}
	
	@JsonProperty("item_id")
	private String itemId;
	
	@JsonProperty
	private String revision;
	
	@JsonProperty
	private String modifier;
	
	@JsonProperty
	private String reason;
	
	public String getItemId(){
		return itemId;
	}
	
	public String getRevision(){
		return revision;
	}
	
	public String getModifier(){
		return modifier;
	}
	
	public String getReason(){
		return reason;
	}
	
	public void setItemId(String itemId){
		this.itemId = itemId;
	}
	
	public void setRevision(String revision){
		this.revision = revision;
	}
	
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	
	public void setReason(String reason){
		this.reason = reason;
	}
}
