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
@Table(name = "groups")
@JsonRootName("group")
@NamedQueries({
	@NamedQuery(name = "groups.getAll", query = "FROM Group p")
})
public class Group {
	
	public Group(){}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty
	private long id;
	
	@Column(name = "created_by", nullable = false, length = 32)
	@JsonProperty("created_by")
	private String createdBy;
	
	@Column(name = "created_date", nullable = false)
	@JsonProperty("created_date")
	private Date createdDate;
	
	public void setId(long id) { this.id = id; }
	public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
	public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
	
	public long getId() { return id; }
	public String getCreatedBy() { return createdBy; }
	public Date getCreatedDate() { return createdDate; }
}
