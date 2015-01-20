package com.nehp.rfid_system;

import org.junit.Before;
import org.junit.Test;

import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.data.ItemDAO;
import static org.fest.assertions.Assertions.assertThat;

public class ItemDAOTest extends DAOTest {

	ItemDAO itemDAO;
	
	@Before
	public void initialize() {
		itemDAO = new ItemDAO(sessionFactory);
	}
	
	@Test
	public void getItemById(){
		long id = 1L;
		
		getSession().beginTransaction();
		Item item = itemDAO.getItemById(id).get();
		getSession().getTransaction().commit();
		
		assertThat(item.getId()).isEqualTo(id);
	}
	
}
