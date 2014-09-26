package com.nehp.rfid_system.server.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;
import static javax.persistence.GenerationType.IDENTITY;


@JsonSnakeCase
@Entity
@Table(name = "items")
@NamedQueries({
		@NamedQuery(name = "items.getAll", query = "FROM Items p"),
		@NamedQuery(name = "items.getByStage", query = "FROM Items p WHERE p.currentStage = :stage"),
		@NamedQuery(name = "items.getById", query = "FROM Items p WHERE p.id = :id") })
public class Item {

	@JsonProperty
	private Long id;
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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonProperty
	public long getID() {
		return id;
	}

	@JsonProperty
	public void setID(Long id) {
		this.id = id;
	}
	
	@Column(name = "RFID", nullable = false, length = 32)
	@JsonProperty
	public String getRFID() {
		return rfid;
	}

	@JsonProperty
	public void setRFID(String rfid) {
		this.rfid = rfid;
	}

	@Column(name = "itemID", nullable = false, length = 64)
	@JsonProperty
	public String getItemID() {
		return itemId;
	}

	@JsonProperty
	public void setItemID(String itemId) {
		this.itemId = itemId;
	}
	
	@Column(name = "description", nullable = false, length = 128)
	@JsonProperty
	public String getDescription() {
		return description;
	}

	@JsonProperty
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "createdBy", nullable = false, length = 32)
	@JsonProperty
	public String getCreatedBy() {
		return createdBy;
	}

	@JsonProperty
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "createdDate", nullable = false, length = 24)
	@JsonProperty
	public String getCreatedDate() {
		return createdDate;
	}

	@JsonProperty
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "currentRevision", nullable = true, length = 4)
	@JsonProperty
	public long getCurrentRevision() {
		return id;
	}

	@JsonProperty
	public void setCurrentRevision(String currentRevision) {
		this.currentRevision = currentRevision;
	}
	
	@Column(name = "currentRevisionDate", nullable = true, length = 24)
	@JsonProperty
	public String getCurrentRevisionDate() {
		return currentRevisionDate;
	}

	@JsonProperty
	public void setCurrentRevisionDate(String currentRevisionDate) {
		this.currentRevisionDate = currentRevisionDate;
	}
	
	@Column(name = "currentStage", nullable = false, length = 14)
	@JsonProperty
	public long getCurrentStage() {
		return id;
	}

	@JsonProperty
	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}
	
	@Column(name = "lastStatusChangeUser", nullable = true, length = 32)
	@JsonProperty
	public String getLastStatusChangeUser() {
		return lastStatusChangeUser;
	}

	@JsonProperty
	public void setLastStatusChangeUser(String lastStatusChangeUser) {
		this.lastStatusChangeUser = lastStatusChangeUser;
	}
	
	@Column(name = "lastStatusChangeDate", nullable = true, length = 24)
	@JsonProperty
	public String getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}

	@JsonProperty
	public void setStage1Date(String date) {
		this.stage1Date = date;
	}
	
	@Column(name = "stage1Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage1Date() {
		return stage1Date;
	}
	@JsonProperty
	public void setStage1User(String user) {
		this.stage2User = user;
	}
	
	@Column(name = "stage1User", nullable = true, length = 32)
	@JsonProperty
	public String getStage1User() {
		return stage2User;
	}
	
	@JsonProperty
	public void setStage2Date(String date) {
		this.stage3Date = date;
	}
	
	@Column(name = "stage2Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage2Date() {
		return stage3Date;
	}
	@JsonProperty
	public void setStage2User(String user) {
		this.stage2User = user;
	}
	
	@Column(name = "stage2User", nullable = true, length = 32)
	@JsonProperty
	public String getStage2User() {
		return stage2User;
	}

	@JsonProperty
	public void setStage3Date(String date) {
		this.stage3Date = date;
	}
	
	@Column(name = "stage3Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage3Date() {
		return stage3Date;
	}
	@JsonProperty
	public void setStage3User(String user) {
		this.stage3User = user;
	}
	
	@Column(name = "stage3User", nullable = true, length = 32)
	@JsonProperty
	public String getStage3User() {
		return stage3User;
	}

	@JsonProperty
	public void setStage4Date(String date) {
		this.stage4Date = date;
	}
	
	@Column(name = "stage4Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage4Date() {
		return stage4Date;
	}
	@JsonProperty
	public void setStage4User(String user) {
		this.stage4User = user;
	}
	
	@Column(name = "stage4User", nullable = true, length = 32)
	@JsonProperty
	public String getStage4User() {
		return stage4User;
	}

	@JsonProperty
	public void setStage5Date(String date) {
		this.stage5Date = date;
	}
	
	@Column(name = "stage5Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage5Date() {
		return stage5Date;
	}
	@JsonProperty
	public void setStage5User(String user) {
		this.stage5User = user;
	}
	@Column(name = "stage5User", nullable = true, length = 32)
	@JsonProperty
	public String getStage5User() {
		return stage5User;
	}

	@JsonProperty
	public void setStage6Date(String date) {
		this.stage6Date = date;
	}
	
	@Column(name = "stage6Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage6Date() {
		return stage6Date;
	}

	@JsonProperty
	public void setStage6User(String user) {
		this.stage6User = user;
	}
	
	@Column(name = "stage6User", nullable = true, length = 32)
	@JsonProperty
	public String getStage6User() {
		return stage6User;
	}

	@JsonProperty
	public void setStage7Date(String date) {
		this.stage7Date = date;
	}
	
	@Column(name = "stage7Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage7Date() {
		return stage7Date;
	}
	@JsonProperty
	public void setStage7User(String user) {
		this.stage7User = user;
	}
	
	@Column(name = "stage7User", nullable = true, length = 32)
	@JsonProperty
	public String getStage7User() {
		return stage7User;
	}

	@JsonProperty
	public void setStage0Date(String date) {
		this.stage0Date = date;
	}
	
	@Column(name = "stage0Date", nullable = true, length = 24)
	@JsonProperty
	public String getStage0Date() {
		return stage0Date;
	}
	
	@JsonProperty
	public void setStage0User(String user) {
		this.stage0User = user;
	}
	
	@Column(name = "stage0User", nullable = true, length = 32)
	@JsonProperty
	public String getStage0User() {
		return stage0User;
	}
}
