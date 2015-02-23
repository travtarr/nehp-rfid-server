package com.nehp.rfid_system.server.core;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import io.dropwizard.jackson.JsonSnakeCase;

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

@JsonSnakeCase
@Entity
@Table(name = "stagelog")
@JsonRootName("stagelog")
@NamedQueries({
	@NamedQuery(name = "stagelog.getAll", query = "FROM StageLog p"),
	@NamedQuery(name = "stagelog.getByItem", query = "FROM StageLog p WHERE p.item = :item")
})
public class StageLog {
	
	public StageLog(){}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty
	private Long id;
	
	@Column(name = "item")
	@JsonProperty("item")
	private Long item;
	
	@Column(name = "stage")
	@JsonProperty("stage")
	private int stage;
	
	@Column(name = "description")
	@JsonProperty("description")
	private String description;
	
	@Column(name ="stage_date")
	@JsonProperty("stage_date")
	private Date stageDate;
	
	@Column(name = "signedby")
	@JsonProperty("signed_by")
	private String signedBy;
	
	@Column(name = "reason")
	@JsonProperty("reason")
	private String reason;
	
	public Long getId() {
		return id;
	}
	
	public Long getItem() {
		return item;
	}
	
	public int getStage() {
		return stage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getSignedBy() {
		return signedBy;
	}
	
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public Date getStageDate() {
		return stageDate;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setItem(Long item) {
		this.item = item;
	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public void setStageDate(Date stageDate) {
		this.stageDate = stageDate;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
