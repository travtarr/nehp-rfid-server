package com.nehp.rfid_system.server.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.Signature;
import com.nehp.rfid_system.server.core.StageLog;
import com.nehp.rfid_system.server.data.ItemDAO;
import com.nehp.rfid_system.server.data.SignatureDAO;
import com.nehp.rfid_system.server.data.StageLogDAO;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/signature")
public class SignatureResource {
	
	private final SignatureDAO dao;
	private final ItemDAO itemDAO;
	private final StageLogDAO stageDAO;
	
	public SignatureResource(SignatureDAO dao, ItemDAO items, StageLogDAO stageDAO){
		this.dao = dao;
		this.itemDAO = items;
		this.stageDAO = stageDAO;
	}
	
	
	/**
	 * Gets a signature based upon the stage of the item.
	 * 
	 * @param stage - ID of the stage to get signature of
	 * @return base64 encoded string of binary image data
	 */
	@GET
	@Timed
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response getByStage(@QueryParam("stage") String stage) {
		Optional<Signature> optSig = dao.getByStage(Long.parseLong(stage));
		if (optSig.isPresent()){
			Signature sig = optSig.get();
			return Response.status( Response.Status.OK ).entity( Base64.encodeBase64String( sig.getImage()) ).build();
		} else {
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
	
	
	/**
	 * Gets a signature based upon the ID of the signature.
	 * 
	 * @param id - signature's unique ID
	 * @return base64 encoded string of binary image data
	 */
	@GET
	@Path("/{sig}")
	@Timed
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response getById(@PathParam("sig") String id) {
		Optional<Signature> optSig = dao.getById(Long.parseLong(id));
		if (optSig.isPresent()){
			Signature sig = optSig.get();
			return Response.status( Response.Status.OK ).entity( Base64.encodeBase64URLSafeString( sig.getImage()) ).build();
		} else {
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
    
	
	/**
	 * Creates a signature for the given item.
	 * 
	 * @param file InputStream of binary image data
	 * @param fileDisp file details
	 * @param itemList concatenated string of item IDs
	 * @param name last name of the person that completed the transaction
	 * @return Success = HTTP Status Code 200 with newly created ID as entity
	 * @return Failure = HTTP Status Code 400
	 */
	@POST
	@Timed
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response create(@FormDataParam("file") InputStream file, 
			@FormDataParam("file") FormDataContentDisposition fileDisp, 
			@FormDataParam("item") String itemid, 
			@FormDataParam("name") String name) {
		
		boolean success;
		byte[] image = new byte[(int) fileDisp.getSize()];
		
		// read the input stream into a local binary variable
		try {
			file.read(image);
			success = true;
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		
		// make sure the image was copied before creating the record
		if ( success ) {
			Item item = null;
			Optional<Item> optItem = itemDAO.getItemById(Long.parseLong(itemid));
			if (optItem.isPresent())
				item = optItem.get();
			if (item != null) {
				Signature sig = new Signature();
				sig.setItem(item.getId());
				StageLog stageLog = stageDAO.getById( item.getCurrentStage() ).get();
				sig.setStage(stageLog.getId());
				sig.setImage(image);
				sig.setAuthor(name);
				Long newId = dao.create(sig);
				
				if ( newId != null ){
					return Response.status( Response.Status.OK ).entity( newId ).build();
				} else {
					return Response.status( Response.Status.BAD_REQUEST ).build();
				}
			}
		} 
		return Response.status( Response.Status.BAD_REQUEST ).build();
	}
	
	/**
	 * Creates a signature record for multiple items.
	 * 
	 * @param file InputStream of binary image data
	 * @param fileDisp file details
	 * @param itemList concatenated string of item IDs
	 * @param name last name of the person that completed the transaction
	 * @return Success = HTTP Status Code 200 with newly created ID as entity
	 * @return Failure = HTTP Status Code 400
	 */
	@POST
	@Timed
	@Path("/multi")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response createMulti(@FormDataParam("file") InputStream file, 
			@FormDataParam("file") FormDataContentDisposition fileDisp, 
			@FormDataParam("item") String itemList, 
			@FormDataParam("name") String name ){
		
		boolean success;
		byte[] image = new byte[(int) fileDisp.getSize()];
		
		// read the input stream into a local binary variable
		try {
			file.read(image);
			success = true;
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
	
		// make sure the image was copied before creating the records
		if ( success ) {
			String[] itemArray = itemList.split(":");
			Long[] newList = new Long[itemArray.length];
			
			// go through each item
			for (int i = 0; i < itemArray.length; i++){
				if ( itemArray[i] != "" ) {
					Item item = null;
					Optional<Item> optItem = itemDAO.getItemById(Long.parseLong(itemArray[i]));
					if (optItem.isPresent())
						item = optItem.get();
					
					if (item != null) {
						Signature sig = new Signature();
						sig.setItem(item.getId());
						StageLog stageLog = stageDAO.getById( item.getCurrentStage() ).get();
						sig.setStage(stageLog.getId());
						sig.setImage(image);
						sig.setAuthor(name);
						newList[i] = dao.create(sig);
					} 
				}
			}
			
			// just check the first one, may need to check all in the future
			if ( newList[0] > 0 ){
				return Response.status( Response.Status.OK ).entity( newList ).build();
			} else {
				return Response.status( Response.Status.BAD_REQUEST ).build();
			}
		} else {
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
}
