package com.nehp.rfid_system.server.core;

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
@Table(name = "signature")
@JsonRootName("signature")
@NamedQueries({
	@NamedQuery(name = "signature.getByItemAndStage", query = "FROM Signature p WHERE p.item = :item AND p.stage = :stage"),
	@NamedQuery(name = "signature.getAllByItem", query = "FROM Signature p WHERE p.item = :item"),
	@NamedQuery(name = "signature.getAll", query = "FROM Signature")
})
public class Signature {
	
	public Signature() {}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@JsonProperty
	private long id;
	
	@JsonProperty
	private String item;
	
	@JsonProperty
	private String stage;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime created;
	
	@Type(type="binary")
	private byte[] image;
	
	public void setId(long id){
		this.id = id;
	}
	
	public void setItem(String item){
		this.item = item;
	}
	
	public void setStage(String stage){
		this.stage = stage;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setCreated(DateTime created){
		this.created = created;
	}
	
	public void setImage(byte[] image){
		this.image = image;
	}
	
	public long getId(){
		return id;
	}
	
	public String getItem(){
		return item;
	}
	
	public String getStage(){
		return stage;
	}
	
	public String getName(){
		return name;
	}
	
	public DateTime getCreated(){
		return created;
	}
	
	public byte[] getImage(){
		return image;
	}
}
