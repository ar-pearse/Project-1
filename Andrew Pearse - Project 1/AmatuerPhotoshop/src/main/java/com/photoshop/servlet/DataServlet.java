package com.photoshop.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name="data", urlPatterns = {"*.json"})
public class DataServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(DataServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		new RequestForwarder().data(req, resp);
		logger.info("Request sent through DataServlet");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		new RequestForwarder().data(req, resp);
		logger.info("Request sent through DataServlet");
	}
}
