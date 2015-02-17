package com.nehp.rfid_system;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

import com.nehp.rfid_system.server.helpers.PasswordHelper;

public class PasswordHelperTest {

	@Test
	public void testSalting() throws Exception{
		String original = "test";
		//String salted = "TvQD49VTGDv9Ct5/dUVC5Zmvx5X+vGTg6EwCgWeeb+I=$x4aYkC+lzqoCSjPFvg+52x045adYqoMvzYRfsBlhPpc=";
		//String original = PasswordHelper.randomizedPassword();
		String salted = PasswordHelper.getSaltedHash(original);		
		System.out.println(original);
		System.out.println(salted + " , length: " + salted.length());
		boolean result = PasswordHelper.check(original, salted);
		assertThat(result).isTrue();
	}
}
