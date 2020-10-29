package com.photoshop.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet (name = "views", urlPatterns = {"*.page"})
public class ViewServlet extends HttpServlet{

	private static Logger logger = Logger.getLogger(ViewServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("Request sent through ViewServlet");
		req.getRequestDispatcher(
				new RequestForwarder().routes(req)).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("Request sent through ViewServlet");
		req.getRequestDispatcher(
				new RequestForwarder().routes(req)).forward(req, resp);
	}
}
