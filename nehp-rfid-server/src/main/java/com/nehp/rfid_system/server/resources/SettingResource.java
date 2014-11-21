package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Setting;
import com.nehp.rfid_system.server.core.SettingList;
import com.nehp.rfid_system.server.core.SettingWrap;
import com.nehp.rfid_system.server.data.SettingDAO;



@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
public class SettingResource {
	
	private final SettingDAO settingDAO;
	private final Long ADMIN = 1L;
	
	public SettingResource(SettingDAO dao){
		this.settingDAO = dao;
	}
	
	@GET
	@Timed
	@UnitOfWork
	@RestrictedTo({Authority.ROLE_USER})
	public SettingList getAll(@QueryParam("user") String user, @QueryParam("admin") Boolean admin){
		SettingList list = new SettingList();
		if(user != null && !user.isEmpty()){
			list.setSettings(settingDAO.getByUserIdList(Long.parseLong(user)));
		} else if(admin != null && admin) {
			list.setSettings(settingDAO.getByUserIdList(ADMIN));
		} else {
			list.setSettings(settingDAO.getAll());
		}
		return list;
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/{id}")
	@RestrictedTo({Authority.ROLE_USER})
	public SettingWrap getById(@PathParam("id") String id){
		SettingWrap wrap = new SettingWrap();
		wrap.setSetting(settingDAO.getByUserId(Long.parseLong(id)));
		return wrap;
	}
		
	@PUT
	@Timed
	@Path("/{id}")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo({Authority.ROLE_USER})
	public Response updateById(@PathParam("id") String id, @Valid SettingWrap setting){
		Setting persistedSetting = setting.getSetting();
		if(Long.parseLong(id) == ADMIN){
			return Response.status( Response.Status.UNAUTHORIZED ).build();
		} 
		boolean success = settingDAO.update(Long.parseLong(id), persistedSetting);
		
		SettingWrap wrap = new SettingWrap();
		if( success ){
			wrap.setSetting( persistedSetting );
			return Response.status( Response.Status.OK ).entity( wrap ).build();
		} else
			return Response.status( Response.Status.BAD_REQUEST ).build();
	}
	
	@PUT
	@Timed
	@Path("/admin")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo({Authority.ROLE_ADMIN})
	public Response updateByAdmin(@Valid SettingWrap setting){
		Setting persistedSetting = setting.getSetting();
		boolean success = settingDAO.update(persistedSetting.getId(), persistedSetting);
		
		SettingWrap wrap = new SettingWrap();
		if( success ){
			wrap.setSetting( persistedSetting );
			return Response.status( Response.Status.OK ).entity( wrap ).build();
		} else
			return Response.status( Response.Status.BAD_REQUEST ).build();
	}
}
