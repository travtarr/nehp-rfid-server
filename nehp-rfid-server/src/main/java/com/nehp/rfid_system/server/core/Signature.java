package com.nehp.rfid_system.server.core;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
	@NamedQuery(name = "signature.getAll", query = "FROM Signature"),
	@NamedQuery(name = "signature.getByStage", query = "FROM Signature p WHERE p.stage = :stage")
})
public class Signature {
	
	public Signature() {}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@JsonProperty
	private Long id;
	
	@JsonProperty
	private Long item;
	
	@JsonProperty
	private Long stage;
	
	@JsonProperty
	private String author;
	
	@JsonProperty
	private Date created;
	
	@Type(type="binary")
	private byte[] image;
	
	public void setId(long id){
		this.id = id;
	}
	
	public void setItem(Long item){
		this.item = item;
	}
	
	public void setStage(Long stage){
		this.stage = stage;
	}
	
	public void setAuthor(String name){
		this.author = name;
	}
	
	public void setCreated(Date created){
		this.created = created;
	}

	
	public void setImage(byte[] image){
		this.image = image;
	}
	
	public long getId(){
		return id;
	}
	
	public Long getItem(){
		return item;
	}
	
	public Long getStage(){
		return stage;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public Date getCreated(){
		return created;
	}
	
	public byte[] getImage(){
		return image;
	}
}
