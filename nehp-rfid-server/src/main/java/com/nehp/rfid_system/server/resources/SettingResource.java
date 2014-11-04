package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.SettingWrap;
import com.nehp.rfid_system.server.data.SettingDAO;

@Path("/setting")
@Produces(MediaType.APPLICATION_JSON)
public class SettingResource {
	
	private final SettingDAO settingDAO;
	
	public SettingResource(SettingDAO dao){
		this.settingDAO = dao;
	}
		
	@GET
	@Timed
	@UnitOfWork
	@Path("/{id}")
	@RestrictedTo({Authority.ROLE_USER})
	public SettingWrap getById(@PathParam("id") String id){
		SettingWrap wrap = new SettingWrap();
		wrap.setSetting(settingDAO.getById(Long.parseLong(id)));
		return wrap;
	}
	
	@GET
	@Timed
	@UnitOfWork
	@Path("/{id}/ByUserId")
	@RestrictedTo({Authority.ROLE_USER})
	public SettingWrap getByUserId(@PathParam("id") String id){
		SettingWrap wrap = new SettingWrap();
		wrap.setSetting(settingDAO.getByUserId(Long.parseLong(id)));
		return wrap;
	}
	
	@PUT
	@Timed
	@UnitOfWork
	@Path("/[id]")
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo({Authority.ROLE_USER})
	public String updateById(@PathParam("id") String id, @Valid SettingWrap setting){
		if(settingDAO.update(setting.getSetting()))
			return "Setting updated successfully";
		else
			return "Setting was not updated";
	}
}
