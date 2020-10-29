package com.photoshop.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.photoshop.service.UserService;

public class UserServiceTest {

	UserService us;
	
	@Before
	public void startup() {
		us = new UserService();
	}
	
	@Test
	public void testVerifyUserInTable() {
		assertTrue(us.verifyUser("andrew.roy.pearse@gmail.com", "password"));
	}
	
	@Test
	public void testFindAll() {
		assertNotEquals(0, us.findAll().size());
	}
	
	@Test
	public void testFindById() {
		assertNotNull(us.findById(2));
	}
	
	@Test
	public void testFindByEmail() {
		assertNotNull(us.findByEmail("andrew.roy.pearse@gmail.com"));
	}
	
	@Test
	public void testUpdateUserInformation() {
		assertEquals(1, us.updateUserInformation(us.findByEmail("andrew.roy.pearse@gmail.com")));
	}
}
