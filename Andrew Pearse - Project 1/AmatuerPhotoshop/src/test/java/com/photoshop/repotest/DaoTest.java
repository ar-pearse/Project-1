package com.photoshop.repotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.photoshop.model.Reimbursement;
import com.photoshop.model.User;
import com.photoshop.model.UserRole;
import com.photoshop.repo.ReimbursementDao;
import com.photoshop.repo.UserDao;


public class DaoTest {

	private UserDao ud;
	private ReimbursementDao rd;
	
	@Before
	public void startup() {
		ud = new UserDao();
		rd = new ReimbursementDao();
	}
	
	@Test
	public void findAllTestUserIsNotEmpty() {
		assertTrue(!ud.findAll().isEmpty());
	}
	
	@Test
	public void findByIdUserNotNull() {
		assertNotNull(ud.findById(2));
	}
	
	@Test
	public void findByIdUserEqualsMyUsername() {
		assertEquals("arpearse", ud.findByEmail("andrew.roy.pearse@gmail.com").getUsername());
	}
	
	@Test
	public void createUserEqualsZero() {
		assertEquals(0, ud.create(new User(0, "sbeve", "123", "Steve", "FromMinecraft", "steve_fromminecraft@mojang.com", new UserRole(1, ""))));
	}
	
	@Test
	public void updateIncorrectUserReturnsZero() {
		assertEquals(0, ud.update(new User(0, null, null, null, null, null, new UserRole(0, null))));
	}
	
	@Test
	public void testVerifyUser() {
		assertTrue(ud.verifyUser("andrew.roy.pearse@gmail.com", "password"));
	}
	
	@Test
	public void findAllTestReimbursementIsNotEmpty() {
		assertTrue(!rd.findAll().isEmpty());
	}

	@Test
	public void findByIdReimbursementNotNull() {
		assertNotNull(rd.findById(4));
	}
	
	@Test
	public void updateIncorrectReimbursementReturnsZero() {
		Reimbursement r = rd.findById(4);
		r.setId(0);
		r.setResolver(ud.findById(2));
		assertEquals(0, rd.update(r));
	}
	
	@Test
	public void testFindAllByUser() {
		assertNotEquals(0, rd.findAllByUser(2));
	}
}
