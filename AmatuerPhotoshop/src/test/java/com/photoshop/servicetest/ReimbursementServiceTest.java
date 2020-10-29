package com.photoshop.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.photoshop.service.ReimbursementService;

public class ReimbursementServiceTest {

	private ReimbursementService rs;

	@Before
	public void startup() {
		rs = new ReimbursementService();
	}
	
	@Test
	public void testFindAll() {
		assertTrue(!rs.findAll().isEmpty());
	}
	
	@Test
	public void testFindAllPending() {
		assertTrue(!rs.findAllPending().isEmpty());
	}
	
	@Test
	public void testSortByEmployee() {
		assertTrue(!rs.sortByEmployee(2).isEmpty());
	}
	
	@Test
	public void testFindById() {
		assertNotNull(rs.findById(9));
	}
	
	@Test
	public void testUpdateRequest() {
		assertEquals(1, rs.updateRequest(rs.findById(9)));
	}
	
	@Test
	public void testFindAllByUser() {
		assertTrue(!rs.findAllByUser(2).isEmpty());
	}
}
