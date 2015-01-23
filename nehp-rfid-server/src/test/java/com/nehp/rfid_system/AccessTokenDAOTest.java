package com.nehp.rfid_system;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

import com.nehp.rfid_system.server.core.AccessToken;
import com.nehp.rfid_system.server.data.AccessTokenDAO;

public class AccessTokenDAOTest extends DAOTest{

	AccessTokenDAO accessTokenDAO;
	private static final long ID = 1;
	
	@Before
	public void initialize(){
		accessTokenDAO = new AccessTokenDAO(sessionFactory);
	}
	
	@Test
	public void testGenerateAccessTokenAndFindById(){
		getSession().beginTransaction();
		AccessToken accessToken = accessTokenDAO.generateNewAccessToken(ID, new Date());
		assertThat(accessToken.getId()).isEqualTo(accessTokenDAO.findAccessTokenById(accessToken.getId()).get().getId());
		getSession().getTransaction().commit();
	}
	
	@Test
	public void testSetLastAccessTime(){
		getSession().beginTransaction();
		AccessToken accessToken = accessTokenDAO.generateNewAccessToken(ID, new Date());
		Date date = new Date();
		accessTokenDAO.setLastAccessTime(accessToken.getId(), date);
		assertThat(accessTokenDAO.findAccessTokenById(accessToken.getId()).get().getLastAccess()).isEqualTo(date);
		getSession().getTransaction().commit();
	}

	
}
