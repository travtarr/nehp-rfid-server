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
	private String rfid;
	
	@JsonProperty
	private String modifier;
	
	public String getItemId(){
		return itemId;
	}
	
	public String getRevision(){
		return revision;
	}
	
	public String getRfid(){
		return rfid;
	}
	
	public String getModifier(){
		return modifier;
	}
	
	public void setItemId(String itemId){
		this.itemId = itemId;
	}
	
	public void setRevision(String revision){
		this.revision = revision;
	}
	
	public void setRfid(String rfid){
		this.rfid = rfid;
	}
	
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
}
