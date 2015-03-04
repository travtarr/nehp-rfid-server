package com.nehp.rfid_system.server.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

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

	public DownloadResource(String filename) {
		this.filename = filename;
	}

	/**
	 * Sends the specified file to the user. Currently just the
	 * NEHPTrackerCab.CAB file.
	 * 
	 * @return output stream of file
	 */
	@GET
	@Timed
	@UnitOfWork
	@RestrictedTo(Authority.ROLE_SCANNER)
	public Response download() {
		StreamingOutput stream = null;
		final java.nio.file.Path path = Paths.get(filename);

		if (!Files.isReadable(path))
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();

		// get the file size so the mobile unit knows its progress
		long fileSize = 0;
		try {
			fileSize = Files.size(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		stream = new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException,
					WebApplicationException {
				try {
					Files.copy(path, output);

				} catch (Exception e) {
					throw new WebApplicationException(e);
				} finally {
					if (output != null)
						output.close();
				}
			}
		};

		return Response.ok(stream)
				.header("Content-Length", String.valueOf(fileSize)).build();
	}
}
