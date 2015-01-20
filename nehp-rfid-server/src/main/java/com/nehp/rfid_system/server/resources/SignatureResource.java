package com.nehp.rfid_system.server.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.json.JSONArray;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Signature;
import com.nehp.rfid_system.server.data.SignatureDAO;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/signature")
public class SignatureResource {
	
	private final SignatureDAO dao;
	
	public SignatureResource(SignatureDAO dao){
		this.dao = dao;
	}
     
	@POST
	@Timed
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_SCANNER)
	public Response create(@FormDataParam("file") InputStream file, 
			@FormDataParam("file") FormDataContentDisposition fileDisp, 
			@FormDataParam("item") String item, 
			@FormDataParam("stage") String stage,
			@FormDataParam("name") String name){
		
		boolean success;
		byte[] image = new byte[(int) fileDisp.getSize()];
		try {
			file.read(image);
			success = true;
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		// make sure the image was copied before creating the record
		if( success ) {
			Signature sig = new Signature();
			sig.setItem(item);
			sig.setStage(stage);	
			sig.setImage(image);
			sig.setName(name);
			sig.setCreated(DateTime.now());
			Long newId = dao.create(sig);
			
			if ( newId != null ){
				return Response.status( Response.Status.OK ).entity( newId ).build();
			} else {
				return Response.status( Response.Status.BAD_REQUEST ).build();
			}
		} else {
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
	
	@POST
	@Timed
	@Path("/multi")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_SCANNER)
	public Response createMulti(@FormDataParam("file") InputStream file, 
			@FormDataParam("file") FormDataContentDisposition fileDisp, 
			@FormDataParam("items") JSONArray items, 
			@FormDataParam("stage") String stage){
		
		boolean success;
		byte[] image = new byte[(int) fileDisp.getSize()];
		try {
			file.read(image);
			success = true;
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		// make sure the image was copied before creating the records
		if( success ) {
			Long[] newList = new Long[items.length()];
			// go through each item in JSONArray
			for (int i = 0; i < items.length(); i++){				
				Signature sig = new Signature();
				sig.setItem(items.getString(i));
				sig.setStage(stage);	
				sig.setImage(image);
				sig.setCreated(DateTime.now());
				newList[i] = dao.create(sig);
				
			}
			
			// just check the first one, may need to check all in the future
			if ( newList[0] != null ){
				return Response.status( Response.Status.OK ).entity( new JSONArray(newList) ).build();
			} else {
				return Response.status( Response.Status.BAD_REQUEST ).build();
			}
		} else {
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
}
