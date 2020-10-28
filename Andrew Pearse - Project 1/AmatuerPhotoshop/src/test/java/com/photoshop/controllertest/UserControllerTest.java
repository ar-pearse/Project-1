package com.photoshop.controllertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.photoshop.controller.UserController;
import com.photoshop.util.SessionController;

public class UserControllerTest {

	UserController uc;
	
	@Before
	public void startup() {
		uc = new UserController();
	}
	
	@Test
	public void testVerifyUserWithEmployee() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		SessionController sc = mock(SessionController.class);
		HttpSession hs = mock(HttpSession.class);
		
		when(req.getParameter("email")).thenReturn("andrew.roy.pearse@gmail.com");
		when(req.getParameter("password")).thenReturn("password");
		when(req.getSession()).thenReturn(hs);
		
		assertEquals("html/employee.html", uc.verifyUser(req));
		
	}
	
	@Test
	public void testVerifyUserWithFinanceManager() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		SessionController sc = mock(SessionController.class);
		HttpSession hs = mock(HttpSession.class);
		
		when(req.getParameter("email")).thenReturn("jlawn@gmail.com");
		when(req.getParameter("password")).thenReturn("longgone");
		when(req.getSession()).thenReturn(hs);
		
		assertEquals("html/finance manager.html", uc.verifyUser(req));
	}
	
	@Test
	public void testVerifyInvalidUser() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		when(req.getParameter("email")).thenReturn("andrew.roy.pearse@gmail.com");
		when(req.getParameter("password")).thenReturn("bad password");
		
		assertEquals("html/login.html", uc.verifyUser(req));
	}
}
