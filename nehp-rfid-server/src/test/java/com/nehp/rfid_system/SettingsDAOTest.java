package com.nehp.rfid_system;

import org.junit.Before;
import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;
import com.nehp.rfid_system.server.core.Setting;
import com.nehp.rfid_system.server.data.SettingDAO;

import static org.junit.Assert.assertNotNull;


public class SettingsDAOTest extends DAOTest {
	
	SettingDAO dao;
	
	@Before
	public void initialize() {
		dao = new SettingDAO(sessionFactory);
	}
	
	@Test
	public void testGetById(){
		Long id = 1L;
		
		getSession().beginTransaction();
		Setting setting = dao.getById(id);
		getSession().getTransaction().commit();
		
		assertNotNull(setting);
	}
	
	@Test
	public void updateSuccessful(){
		Setting setting = new Setting();
		setting.setId(1L);
		setting.setUser(1L);
		setting.setUserChanged(false);
		setting.setStage(1);
		setting.setDuration(5);
		
		getSession().beginTransaction();
		boolean success = dao.update(setting);
		getSession().getTransaction().commit();
		
		assertThat(success).isTrue();
	}
}
