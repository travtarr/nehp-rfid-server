package com.nehp.rfid_system.server.core;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@Entity
@Table(name = "items")
@NamedQueries({
		@NamedQuery(name = "items.findAll", query = "SELECT p FROM Items p"),
		@NamedQuery(name = "items.findByType", query = "SELECT p FROM Items p WHERE p.currentStage = :type"),
		@NamedQuery(name = "items.findById", query = "SELECT p FROM Items p WHERE p.id = :id") })
public class Item {

	@JsonProperty
	private long id;
	@JsonProperty
	private String rfid;
	@JsonProperty
	private String itemId;
	
	@JsonProperty
	private String description;
	
	@JsonProperty
	private String createdBy;
	@JsonProperty
	private String createdDate;
	
	@JsonProperty
	private String currentRevision;
	@JsonProperty
	private String currentRevisionDate;
	@JsonProperty
	private String currentStage;
	
	@JsonProperty
	private String lastStatusChangeDate;
	@JsonProperty
	private String lastStatusChangeUser;
	
	@JsonProperty
	private String stage1Date;
	@JsonProperty
	private String stage1User;
	
	@JsonProperty
	private String stage2Date;
	@JsonProperty
	private String stage2User;
	
	@JsonProperty
	private String stage3Date;
	@JsonProperty
	private String stage3User;
	
	@JsonProperty
	private String stage4Date;
	@JsonProperty
	private String stage4User;
	
	@JsonProperty
	private String stage5Date;
	@JsonProperty
	private String stage5User;
	
	@JsonProperty
	private String stage6Date;
	@JsonProperty
	private String stage6User;
	
	@JsonProperty
	private String stage7Date;
	@JsonProperty
	private String stage7User;
	
	@JsonProperty
	private String stage0Date;
	@JsonProperty
	private String stage0User;

	@JsonProperty
	public long getID() {
		return id;
	}

	@JsonProperty
	public void setID(Long id) {
		this.id = id;
	}
}
