package com.nehp.rfid_system.server.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nehp.rfid_system.server.helpers.CustomDateDeserializer;

import io.dropwizard.jackson.JsonSnakeCase;
import static javax.persistence.GenerationType.IDENTITY;


@JsonSnakeCase
@Entity
@Table(name = "items")
@JsonRootName("item")
@NamedQueries({
		@NamedQuery(name = "items.getAll", query = "FROM Item p"),
		@NamedQuery(name = "items.getByStage", query = "FROM Item p WHERE p.currentStageNum = :stage"),
		@NamedQuery(name = "items.getByRFID", query = "FROM Item p WHERE p.rfid = :rfid"),
		@NamedQuery(name = "items.getByItemId", query = "FROM Item p WHERE p.itemId = :id"),
		@NamedQuery(name = "items.getByItemIdAndRev", query = "FROM Item p WHERE p.itemId = :id AND p.revision = :rev"),
		@NamedQuery(name = "items.getByPrinted", query = "FROM Item p WHERE p.printed = :printed") })
public class Item {

	public Item(){}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty
	private Long id;
	
	@Column(name = "rfid", nullable = true, length = 58)
	@JsonProperty
	private String rfid;
	
	@Column(name = "itemId", nullable = false, length = 64)
	@JsonProperty("item_id")
	private String itemId;
	
	@Column(name = "comment", nullable = true, length = 128)
	@JsonProperty
	private String comment;
	
	@Column(name = "printed", nullable = true)
	@JsonProperty
	private Boolean printed;
	
	@Column(name = "groupId", nullable = true)
	@JsonProperty
	private Long group;
	
	@Column(name = "created_by", nullable = false, length = 32)
	@JsonProperty("created_by")
	private String createdBy;
	
	@Column(name = "created_date", nullable = true)
	@JsonProperty("created_date")
	private Date createdDate;
	
	@Column(name = "revision", nullable = true, length = 4)
	@JsonProperty("revision")
	private String revision;
	
	@Column(name = "reason")
	@JsonProperty("reason")
	private String reason;
	
	@Column(name = "current_stage")
	@JsonProperty("current_stage")
	private Long currentStage;
	
	@Column(name = "current_stage_num")
	@JsonProperty("current_stage_num")
	private int currentStageNum;
	
	@Column(name = "current_stage_desc")
	@JsonProperty("current_stage_desc")
	private String currentStageDesc;
	
	@Column(name = "last_status_change_date", nullable = true)
	@JsonProperty("last_status_change_date")
	private Date lastStatusChangeDate;
	
	@Column(name = "last_status_change_user", nullable = true, length = 32)
	@JsonProperty("last_status_change_user")
	private String lastStatusChangeUser;
	
	/**
	 * Setters & Getters
	 */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRFID() {
		return rfid;
	}

	public void setRFID(String rfid) {
		this.rfid = rfid;
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public Long getGroup() {
		return group;
	}
	
	public void setGroup(Long group) {
		this.group = group;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment (String comment) {
		this.comment = comment ;
	}
	
	public Boolean getPrinted(){
		return printed;
	}
	
	public void setPrinted(Boolean printed){
		this.printed = printed;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Long getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Long currentStage) {
		this.currentStage = currentStage;
	}
	
	public void setCurrentStageNum(int currentStageNum) {
		this.currentStageNum = currentStageNum;
	}
	
	public int getCurrentStageNum() {
		return currentStageNum;
	}
	
	public String getCurrentStageDesc() {
		return currentStageDesc;
	}

	public void setCurrentStageDesc(String currentStageDesc) {
		this.currentStageDesc = currentStageDesc;
	}
	
	public String getLastStatusChangeUser() {
		return lastStatusChangeUser;
	}

	public void setLastStatusChangeUser(String lastStatusChangeUser) {
		this.lastStatusChangeUser = lastStatusChangeUser;
	}
	
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public Date getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}
	
	public void setLastStatusChangeDate(Date lastStatusChangeDate) {
		this.lastStatusChangeDate = lastStatusChangeDate;
	}

}