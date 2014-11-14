package com.nehp.rfid_system;

import org.junit.Before;
import org.junit.Test;

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
		Long id = 2L;
		
		getSession().beginTransaction();
		Setting setting = dao.getById(id);
		getSession().getTransaction().commit();
		
		assertNotNull(setting);
	}
}
