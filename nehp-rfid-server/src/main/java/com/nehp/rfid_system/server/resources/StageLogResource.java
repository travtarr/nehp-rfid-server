package com.nehp.rfid_system.server.resources;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.StageLog;
import com.nehp.rfid_system.server.core.StageLogList;
import com.nehp.rfid_system.server.data.StageLogDAO;

@Path("stagelogs")
public class StageLogResource {

	private StageLogDAO stageDAO;

	public StageLogResource(StageLogDAO stageDAO) {
		this.stageDAO = stageDAO;
	}

	/**
	 * Gets all stage logs.
	 * 
	 * @return list of stage logs
	 */
	@GET
	@Timed
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	@RestrictedTo(Authority.ROLE_USER)
	public StageLogList getAll() {
		StageLogList list = new StageLogList();
		Optional<List<StageLog>> retrievedList = stageDAO.getAll();
		if (retrievedList.isPresent()) {
			list.setStageLog(retrievedList.get());
		}
		return list;
	}
}
