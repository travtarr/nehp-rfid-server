package com.nehp.rfid_system.server.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	
	@GET
	@Path("/{item}/{stage}")
	@Timed
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response getByItemAndRev(@PathParam("item") String item,
			@PathParam("stage") String stage) {
		Optional<Signature> optSig = dao.getByItemAndStage(Long.parseLong(item), Long.parseLong(stage));
		if (optSig.isPresent()){
			Signature sig = optSig.get();
			return Response.status( Response.Status.OK ).entity( Base64.encodeBase64URLSafeString( sig.getImage()) ).build();
		} else {
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
     
	@POST
	@Timed
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_USER)
	public Response create(@FormDataParam("file") InputStream file, 
			@FormDataParam("file") FormDataContentDisposition fileDisp, 
			@FormDataParam("item") String itemid, 
			@FormDataParam("name") String name,
			@FormDataParam("revision") String revision ){
		
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
			Item item = null;
			Optional<Item> optItem = itemDAO.getItemByItemIdAndRev(itemid, revision);
			if (optItem.isPresent())
				item = optItem.get();
			if (item != null) {
				Signature sig = new Signature();
				sig.setItem(item.getId());
				StageLog stageLog = stageDAO.getById( item.getCurrentStage() ).get();
				// any time a signature is created, the signature that is created is done so before
				// the item is sent to the next stage, so need to grab the next stage
				sig.setStage(stageLog.getStage() + 1);
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
		try {
			file.read(image);
			success = true;
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		// make sure the image was copied before creating the records
		if( success ) {
			System.out.println("Image copied success");
			String[] itemArray = itemList.split(":");
			Long[] newList = new Long[itemArray.length];
			// go through each item
			for (int i = 0; i < itemArray.length; i++){
				System.out.println("item: " + itemArray[i]);
				if (itemArray[i] != "") {
					Item item = null;
					Optional<Item> optItem = itemDAO.getItemById(Long.parseLong(itemArray[i]));
					if (optItem.isPresent())
						item = optItem.get();
					
					if (item != null) {
						System.out.println("Item not null");
						Signature sig = new Signature();
						sig.setItem(item.getId());
						StageLog stageLog = stageDAO.getById( item.getCurrentStage() ).get();
						// any time a signature is created, the signature that is created is done so before
						// the item is sent to the next stage, so need to grab the next stage
						sig.setStage(stageLog.getStage() + 1);
						sig.setImage(image);
						sig.setAuthor(name);
						newList[i] = dao.create(sig);
						System.out.println("new sig id: "+ newList[i]);
					} else {
						System.out.println("Item not found");
					}
					
				}
			}
			
			// just check the first one, may need to check all in the future
			if ( newList[0] > 0 ){
				System.out.println("Success: 200");
				return Response.status( Response.Status.OK ).entity( newList ).build();
			} else {
				System.out.println("No signatures created: 400");
				return Response.status( Response.Status.BAD_REQUEST ).build();
			}
		} else {
			System.out.println("Image copied failed: 400");
			return Response.status( Response.Status.BAD_REQUEST ).build();
		}
	}
}
