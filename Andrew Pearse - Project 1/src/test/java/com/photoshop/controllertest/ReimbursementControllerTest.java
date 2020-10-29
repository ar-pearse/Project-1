package com.photoshop.controllertest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.photoshop.controller.ReimbursementController;
import com.photoshop.model.User;
import com.photoshop.util.SessionController;

public class ReimbursementControllerTest {

	ReimbursementController rc;
	
	@Before
	public void startup() {
		rc = new ReimbursementController();
	}
	
	@Test
	public void testSendAllPendingReimbursements() throws IOException {
		HttpServletResponse res = mock(HttpServletResponse.class);
		PrintWriter pw = mock(PrintWriter.class);
		
		when(res.getWriter()).thenReturn(pw);
		
		assertTrue(rc.sendAllPendingReimbursements(res));
	}
	
	@Test
	public void testSendUserReimbursementRequests() throws IOException {
		HttpServletResponse res = mock(HttpServletResponse.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpSession hs = mock(HttpSession.class);
		PrintWriter pw = mock(PrintWriter.class);
		SessionController sc = mock(SessionController.class);
		User user = mock(User.class);
		res.setContentType("text/json");
		
		when(req.getSession()).thenReturn(hs);
		when(req.getSession().getAttribute("user")).thenReturn(user);
		when(sc.getSessionUser(req)).thenReturn(user);
		when(user.getId()).thenReturn(2);
		
		when(res.getWriter()).thenReturn(pw);
		
		assertTrue(rc.sendUserReimbursementRequests(req, res));
	}
	
}
