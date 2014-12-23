package com.nehp.rfid_system.server.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;


@Path("/download")
public class DownloadResource {

	private String filename;
	
	public DownloadResource(String filename){
		this.filename = filename;
	}
	
	@GET
	@Timed
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_SCANNER)
	public Response download(){
		final InputStream input;
		StreamingOutput stream = null;
		try {
			input = new FileInputStream(filename);
			
			try {
				if ( input.available() == 0 || input.available() == -1 )
					return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).build();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			stream = new StreamingOutput(){
				@Override
				public void write(OutputStream output) throws IOException,
						WebApplicationException {
					int bytes;
					try {
						while ( (bytes = input.read()) != -1) {
							output.write(bytes);
						}
					} catch (Exception e) {
						throw new WebApplicationException(e);
					} finally {
						if (output != null) output.close();
						if (input != null) input.close();
					}
				}
			};
		} catch (FileNotFoundException e1) {
			return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).build();
		}
		
		return Response.ok(stream).build();
	}
}
