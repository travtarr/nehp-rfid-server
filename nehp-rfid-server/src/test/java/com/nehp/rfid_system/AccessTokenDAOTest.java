package com.nehp.rfid_system;

import org.joda.time.DateTime;
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
		DateTime time = new DateTime(2014, 6, 18, 14, 52);
		getSession().beginTransaction();
		AccessToken accessToken = accessTokenDAO.generateNewAccessToken(ID, time);
		assertThat(accessToken.getId()).isEqualTo(accessTokenDAO.findAccessTokenById(accessToken.getId()).get().getId());
		getSession().getTransaction().commit();
	}
	
	@Test
	public void testSetLastAccessTime(){
		DateTime time = new DateTime(2014, 6, 18, 14, 52);
		DateTime newTime = new DateTime(2014, 7, 18, 14, 52);
		getSession().beginTransaction();
		AccessToken accessToken = accessTokenDAO.generateNewAccessToken(ID, time);
		accessTokenDAO.setLastAccessTime(accessToken.getId(), newTime);
		assertThat(accessTokenDAO.findAccessTokenById(accessToken.getId()).get().getLastAccess()).isEqualTo(newTime);
		getSession().getTransaction().commit();
	}

	
}
