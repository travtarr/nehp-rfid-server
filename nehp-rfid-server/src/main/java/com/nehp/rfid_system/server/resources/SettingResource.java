package com.nehp.rfid_system.server.resources;

import java.util.ArrayList;
import java.util.List;

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

	public SettingResource(SettingDAO dao) {
		this.settingDAO = dao;
	}

	/**
	 * Gets all settings.
	 * 
	 * @param user
	 *            (Optional) - id of user
	 * @param admin
	 *            (Optional) - true if requesting only the admin's settings
	 * @return list of settings
	 */
	@GET
	@Timed
	@UnitOfWork
	@RestrictedTo({ Authority.ROLE_USER })
	public SettingList getAll(@QueryParam("user") String user,
			@QueryParam("admin") Boolean admin) {
		SettingList list = new SettingList();
		if (user != null && !user.isEmpty()) {
			list.setSettings(settingDAO.getByUserId(Long.parseLong(user)));
		} else if (admin != null && admin) {
			list.setSettings(settingDAO.getByUserId(ADMIN));
		} else {
			list.setSettings(settingDAO.getAll());
		}
		return list;
	}

	/**
	 * Gets settings based on user's ID.
	 * 
	 * @param id
	 *            - user's unique ID
	 * @return setting
	 */
	@GET
	@Timed
	@UnitOfWork
	@Path("/{id}")
	@RestrictedTo({ Authority.ROLE_USER })
	public SettingWrap getById(@PathParam("id") String id) {
		SettingWrap wrap = new SettingWrap();
		wrap.setSetting(settingDAO.getById(Long.parseLong(id)));
		return wrap;
	}

	/**
	 * Updates a setting based on user's unique ID.
	 * 
	 * @param id
	 * @param setting
	 * @return updated setting on success, status 400 on failure
	 */
	@PUT
	@Timed
	@Path("/{id}")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo({ Authority.ROLE_USER })
	public Response updateById(@PathParam("id") String id,
			@Valid SettingWrap setting) {
		Setting persistedSetting = setting.getSetting();
		persistedSetting.setId(Long.parseLong(id));

		boolean success = settingDAO.update(persistedSetting);

		if (success) {
			SettingWrap wrap = new SettingWrap();
			wrap.setSetting(settingDAO.getById(Long.parseLong(id)));
			return Response.status(Response.Status.OK).entity(wrap).build();
		} else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	/**
	 * Updates the admin's settings.
	 * 
	 * @param list
	 * @return list of updated settings
	 */
	@PUT
	@Timed
	@Path("/admin")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@RestrictedTo({ Authority.ROLE_ADMIN })
	public Response updateByAdmin(@Valid SettingList list) {
		List<Setting> settings = list.getSettings();
		List<Setting> persistedSettings = new ArrayList<Setting>();

		for (Setting setting : settings) {
			boolean success = settingDAO.update(setting);
			if (success)
				persistedSettings.add(settingDAO.getById(setting.getId()));
		}

		if (persistedSettings.size() > 0)
			return Response.status(Response.Status.OK)
					.entity(persistedSettings).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}
}
