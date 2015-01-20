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

import io.dropwizard.jackson.JsonSnakeCase;
import static javax.persistence.GenerationType.IDENTITY;


@JsonSnakeCase
@Entity
@Table(name = "items")
@JsonRootName("item")
@NamedQueries({
		@NamedQuery(name = "items.getAll", query = "FROM Item p"),
		@NamedQuery(name = "items.getByStage", query = "FROM Item p WHERE p.currentStage = :stage"),
		@NamedQuery(name = "items.getByRFID", query = "FROM Item p WHERE p.rfid = :rfid") })
public class Item {

	public Item(){}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty
	private long id;
	
	@Column(name = "rfid", nullable = true, length = 24)
	@JsonProperty
	private String rfid;
	
	@Column(name = "itemid", nullable = false, length = 64)
	@JsonProperty("item_id")
	private String itemId;
	
	@Column(name = "description", nullable = true, length = 128)
	@JsonProperty
	private String description;
	
	@Column(name = "created_by", nullable = false, length = 32)
	@JsonProperty("created_by")
	private String createdBy;
	
	@Column(name = "created_date", nullable = true)
	@JsonProperty("created_date")
	////@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date createdDate;
	
	@Column(name = "current_revision", nullable = true, length = 4)
	@JsonProperty("current_revision")
	private String currentRevision;
	
	@Column(name = "current_revision_date", nullable = true)
	@JsonProperty("current_revision_date")
	////@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date currentRevisionDate;
	
	@Column(name = "current_stage", nullable = true, length = 14)
	@JsonProperty("current_stage")
	private String currentStage;
	
	@Column(name = "last_status_change_date", nullable = true)
	@JsonProperty("last_status_change_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date lastStatusChangeDate;
	
	@Column(name = "last_status_change_user", nullable = true, length = 32)
	@JsonProperty("last_status_change_user")
	private String lastStatusChangeUser;
	
	@Column(name = "stage1_date", nullable = true)
	@JsonProperty("stage1_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage1Date;
	
	@Column(name = "stage1_user", nullable = true, length = 32)
	@JsonProperty("stage1_user")
	private String stage1User;
	
	@Column(name = "stage2_date", nullable = true)
	@JsonProperty("stage2_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage2Date;
	
	@Column(name = "stage2_user", nullable = true, length = 32)
	@JsonProperty("stage2_user")
	private String stage2User;
	
	@Column(name = "stage3_date", nullable = true)
	@JsonProperty("stage3_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage3Date;
	
	@Column(name = "stage3_user", nullable = true, length = 32)
	@JsonProperty("stage3_user")
	private String stage3User;
	
	@Column(name = "stage4_date", nullable = true)
	@JsonProperty("stage4_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage4Date;
	
	@Column(name = "stage4_user", nullable = true, length = 32)
	@JsonProperty("stage4_user")
	private String stage4User;
	
	@Column(name = "stage5_date", nullable = true)
	@JsonProperty("stage5_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage5Date;
	
	@Column(name = "stage5_user", nullable = true, length = 32)
	@JsonProperty("stage5_user")
	private String stage5User;
	
	@Column(name = "stage6_date", nullable = true)
	@JsonProperty("stage6_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage6Date;
	
	@Column(name = "stage6_user", nullable = true, length = 32)
	@JsonProperty("stage6_user")
	private String stage6User;
	
	@Column(name = "stage7_date", nullable = true)
	@JsonProperty("stage7_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage7Date;
	
	@Column(name = "stage7_user", nullable = true, length = 32)
	@JsonProperty("stage7_user")
	private String stage7User;
	
	@Column(name = "stage0_date", nullable = true)
	@JsonProperty("stage0_date")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDate")
	private Date stage0Date;
	
	@Column(name = "stage0_user", nullable = true, length = 32)
	@JsonProperty("stage0_user")
	private String stage0User;
	
	/**
	 * Setters & Getters
	 */
	
	public long getId() {
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public String getCurrentRevision() {
		return currentRevision;
	}

	public void setCurrentRevision(String currentRevision) {
		this.currentRevision = currentRevision;
	}
	
	
	public Date getCurrentRevisionDate() {
		return currentRevisionDate;
	}

	public void setCurrentRevisionDate(Date currentRevisionDate) {
		this.currentRevisionDate = currentRevisionDate;
	}
	
	
	public String getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}
	
	
	public String getLastStatusChangeUser() {
		return lastStatusChangeUser;
	}

	public void setLastStatusChangeUser(String lastStatusChangeUser) {
		this.lastStatusChangeUser = lastStatusChangeUser;
	}
	
	public Date getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}
	
	public void setLastStatusChangeDate(Date lastStatusChangeDate) {
		this.lastStatusChangeDate = lastStatusChangeDate;
	}

	public void setStage1Date(Date date) {
		this.stage1Date = date;
	}
	
	public Date getStage1Date() {
		return stage1Date;
	}

	public void setStage1User(String user) {
		this.stage1User = user;
	}
	
	public String getStage1User() {
		return stage1User;
	}
	
	public void setStage2Date(Date date) {
		this.stage2Date = date;
	}
	
	public Date getStage2Date() {
		return stage2Date;
	}

	public void setStage2User(String user) {
		this.stage2User = user;
	}
	
	public String getStage2User() {
		return stage2User;
	}

	public void setStage3Date(Date date) {
		this.stage3Date = date;
	}
	
	public Date getStage3Date() {
		return stage3Date;
	}

	public void setStage3User(String user) {
		this.stage3User = user;
	}
	
	public String getStage3User() {
		return stage3User;
	}

	public void setStage4Date(Date date) {
		this.stage4Date = date;
	}
	
	public Date getStage4Date() {
		return stage4Date;
	}

	public void setStage4User(String user) {
		this.stage4User = user;
	}
	
	public String getStage4User() {
		return stage4User;
	}

	public void setStage5Date(Date date) {
		this.stage5Date = date;
	}
	
	public Date getStage5Date() {
		return stage5Date;
	}

	public void setStage5User(String user) {
		this.stage5User = user;
	}
	
	public String getStage5User() {
		return stage5User;
	}

	public void setStage6Date(Date date) {
		this.stage6Date = date;
	}
	
	public Date getStage6Date() {
		return stage6Date;
	}

	public void setStage6User(String user) {
		this.stage6User = user;
	}
	
	public String getStage6User() {
		return stage6User;
	}

	public void setStage7Date(Date date) {
		this.stage7Date = date;
	}
	
	public Date getStage7Date() {
		return stage7Date;
	}

	public void setStage7User(String user) {
		this.stage7User = user;
	}
	
	public String getStage7User() {
		return stage7User;
	}

	public void setStage0Date(Date date) {
		this.stage0Date = date;
	}
	
	public Date getStage0Date() {
		return stage0Date;
	}
	
	public void setStage0User(String user) {
		this.stage0User = user;
	}
	
	public String getStage0User() {
		return stage0User;
	}
}
