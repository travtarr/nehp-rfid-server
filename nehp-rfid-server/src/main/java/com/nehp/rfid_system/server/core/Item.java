package com.nehp.rfid_system.server.core;

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

import io.dropwizard.jackson.JsonSnakeCase;
import static javax.persistence.GenerationType.IDENTITY;


@JsonSnakeCase
@Entity
@Table(name = "items")
@JsonRootName("item")
@NamedQueries({
		@NamedQuery(name = "items.getAll", query = "FROM Item p"),
		@NamedQuery(name = "items.getByStage", query = "FROM Item p WHERE p.currentStage = :stage"),
		@NamedQuery(name = "items.getById", query = "FROM Item p WHERE p.id = :id") })
public class Item {

	public Item(){}
	
	
	private long id;
	private String rfid;
	private String itemId;
	private String description;
	private String createdBy;
	private DateTime createdDate;
	private String currentRevision;
	private DateTime currentRevisionDate;
	private String currentStage;
	private DateTime lastStatusChangeDate;
	private String lastStatusChangeUser;
	private DateTime stage1Date;
	private String stage1User;
	private DateTime stage2Date;
	private String stage2User;
	private DateTime stage3Date;
	private String stage3User;
	private DateTime stage4Date;
	private String stage4User;
	private DateTime stage5Date;
	private String stage5User;
	private DateTime stage6Date;
	private String stage6User;
	private DateTime stage7Date;
	private String stage7User;
	private DateTime stage0Date;
	private String stage0User;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "rfid", nullable = false, length = 32)
	@JsonProperty
	public String getRFID() {
		return rfid;
	}

	@JsonProperty
	public void setRFID(String rfid) {
		this.rfid = rfid;
	}

	@Column(name = "itemid", nullable = false, length = 64)
	@JsonProperty
	public String getItemId() {
		return itemId;
	}

	@JsonProperty
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Column(name = "description", nullable = true, length = 128)
	@JsonProperty
	public String getDescription() {
		return description;
	}

	@JsonProperty
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "created_by", nullable = false, length = 32)
	@JsonProperty
	public String getCreatedBy() {
		return createdBy;
	}

	@JsonProperty
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "created_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getCreatedDate() {
		return createdDate;
	}

	@JsonProperty
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "current_revision", nullable = true, length = 4)
	@JsonProperty
	public String getCurrentRevision() {
		return currentRevision;
	}

	@JsonProperty
	public void setCurrentRevision(String currentRevision) {
		this.currentRevision = currentRevision;
	}
	
	@Column(name = "current_revision_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getCurrentRevisionDate() {
		return currentRevisionDate;
	}

	@JsonProperty
	public void setCurrentRevisionDate(DateTime currentRevisionDate) {
		this.currentRevisionDate = currentRevisionDate;
	}
	
	@Column(name = "current_stage", nullable = false, length = 14)
	@JsonProperty
	public String getCurrentStage() {
		return currentStage;
	}

	@JsonProperty
	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}
	
	@Column(name = "last_status_change_user", nullable = true, length = 32)
	@JsonProperty
	public String getLastStatusChangeUser() {
		return lastStatusChangeUser;
	}

	@JsonProperty
	public void setLastStatusChangeUser(String lastStatusChangeUser) {
		this.lastStatusChangeUser = lastStatusChangeUser;
	}
	
	@Column(name = "last_status_change_date", nullable = false)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}
	
	@JsonProperty
	public void setLastStatusChangeDate(DateTime lastStatusChangeDate) {
		this.lastStatusChangeDate = lastStatusChangeDate;
	}

	@JsonProperty
	public void setStage1Date(DateTime date) {
		this.stage1Date = date;
	}
	
	@Column(name = "stage1_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage1Date() {
		return stage1Date;
	}
	@JsonProperty
	public void setStage1User(String user) {
		this.stage1User = user;
	}
	
	@Column(name = "stage1_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage1User() {
		return stage1User;
	}
	
	@JsonProperty
	public void setStage2Date(DateTime date) {
		this.stage2Date = date;
	}
	
	@Column(name = "stage2_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage2Date() {
		return stage2Date;
	}
	@JsonProperty
	public void setStage2User(String user) {
		this.stage2User = user;
	}
	
	@Column(name = "stage2_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage2User() {
		return stage2User;
	}

	@JsonProperty
	public void setStage3Date(DateTime date) {
		this.stage3Date = date;
	}
	
	@Column(name = "stage3_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage3Date() {
		return stage3Date;
	}
	@JsonProperty
	public void setStage3User(String user) {
		this.stage3User = user;
	}
	
	@Column(name = "stage3_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage3User() {
		return stage3User;
	}

	@JsonProperty
	public void setStage4Date(DateTime date) {
		this.stage4Date = date;
	}
	
	@Column(name = "stage4_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage4Date() {
		return stage4Date;
	}
	@JsonProperty
	public void setStage4User(String user) {
		this.stage4User = user;
	}
	
	@Column(name = "stage4_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage4User() {
		return stage4User;
	}

	@JsonProperty
	public void setStage5Date(DateTime date) {
		this.stage5Date = date;
	}
	
	@Column(name = "stage5_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage5Date() {
		return stage5Date;
	}
	@JsonProperty
	public void setStage5User(String user) {
		this.stage5User = user;
	}
	@Column(name = "stage5_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage5User() {
		return stage5User;
	}

	@JsonProperty
	public void setStage6Date(DateTime date) {
		this.stage6Date = date;
	}
	
	@Column(name = "stage6_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage6Date() {
		return stage6Date;
	}

	@JsonProperty
	public void setStage6User(String user) {
		this.stage6User = user;
	}
	
	@Column(name = "stage6_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage6User() {
		return stage6User;
	}

	@JsonProperty
	public void setStage7Date(DateTime date) {
		this.stage7Date = date;
	}
	
	@Column(name = "stage7_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage7Date() {
		return stage7Date;
	}
	@JsonProperty
	public void setStage7User(String user) {
		this.stage7User = user;
	}
	
	@Column(name = "stage7_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage7User() {
		return stage7User;
	}

	@JsonProperty
	public void setStage0Date(DateTime date) {
		this.stage0Date = date;
	}
	
	@Column(name = "stage0_date", nullable = true)
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getStage0Date() {
		return stage0Date;
	}
	
	@JsonProperty
	public void setStage0User(String user) {
		this.stage0User = user;
	}
	
	@Column(name = "stage0_user", nullable = true, length = 32)
	@JsonProperty
	public String getStage0User() {
		return stage0User;
	}
}
