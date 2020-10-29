package com.photoshop.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.photoshop.model.User;

public class SessionController {
	
	public void setSessionUser(HttpServletRequest req, User user) {
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
	}
	
	public User getSessionUser(HttpServletRequest req) {
		return (User) req.getSession().getAttribute("user");
	}
	
	public void invalidate(HttpServletRequest req) {
		req.getSession().invalidate();
	}
}
