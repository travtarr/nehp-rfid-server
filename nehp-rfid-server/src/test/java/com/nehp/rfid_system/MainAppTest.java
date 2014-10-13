package com.nehp.rfid_system;

import org.junit.Test;

import com.nehp.rfid_system.server.helpers.PasswordHelper;

import static org.fest.assertions.Assertions.assertThat;

public class MainAppTest {
	
	@Test
	public void testPasswordHelperFunctions(){
		final String password = "alpha";
		final int length = 89;
		final String passwordHashed = "3u2LiYsD194LYXH89KIlosihAVp8WfP6FPwFpvX5KnI=$NSvc6Rsm5e8UMs+kr5MKBtcXZLc+2NWwanLmvZzNQ8o=";
		
		try {
			String hashed = PasswordHelper.getSaltedHash(password);
			System.out.println(hashed);
			assertThat(hashed.length()).isEqualTo(length);
			assertThat(PasswordHelper.check(password, hashed)).isTrue();
			assertThat(PasswordHelper.check(password, passwordHashed)).isTrue();
		} catch (Exception e) {
			
		}
	}
}
