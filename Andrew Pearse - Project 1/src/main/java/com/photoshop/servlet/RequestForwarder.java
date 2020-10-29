package com.photoshop.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.photoshop.controller.ReimbursementController;
import com.photoshop.controller.UserController;

public class RequestForwarder {

	private UserController uc;
	private ReimbursementController rc;
	
	private static Logger logger = Logger.getLogger(RequestForwarder.class);

	
	public RequestForwarder() {
		this(new UserController(), new ReimbursementController());
	}
	
	public RequestForwarder(UserController uc, ReimbursementController rc) {
		this.uc = uc;
		this.rc = rc;
	}
	
	public String routes(HttpServletRequest req) {
		switch(req.getRequestURI()) {
		case "/AmatuerPhotoshop/login.page":
			logger.info("Routed to employee.html");
			return uc.verifyUser(req);
		case "/AmatuerPhotoshop/employee.page":
			logger.info("Routed to employee.html");
			return "html/employee.html";
		case "/AmatuerPhotoshop/finance.page":
			logger.info("Routed to finance manager.html");
			return "html/finance manager.html";
		case "/AmatuerPhotoshop/request.page":
			logger.info("Routed to new request.html");
			return "html/new request.html";
		case "/AmatuerPhotoshop/create.page":
			logger.info("Routed to employee.html");
			return "html/employee.html";
		default:
			logger.info("Routed to frontpage.html");
			return "html/frontpage.html";
		}
	}
	
	public void data(HttpServletRequest req, HttpServletResponse res) {
		switch(req.getRequestURI()) {
		case "/AmatuerPhotoshop/allUserReimbursements.json":
			rc.sendUserReimbursementRequests(req, res);
			logger.info("Routed to allUserReimbursements.json");
			break;
		case "/AmatuerPhotoshop/reimb.json":
			rc.createNewReimbursement(req);
			logger.info("Routed to reimb.json");
			break;
		case "/AmatuerPhotoshop/cancel.json":
			rc.cancelReimbursement(req);
			logger.info("Routed to cancel.json");
			break;
		case "/AmatuerPhotoshop/allReimbursements.json":
			rc.sendAllPendingReimbursements(res);
			logger.info("Routed to allReimbursements.json");
			break;
		case "/AmatuerPhotoshop/reject.json":
			rc.rejectReimbursement(req, res);
			logger.info("Routed to reject.json");
			break;
		case "/AmatuerPhotoshop/accept.json":
			rc.acceptReimbursement(req, res);
			logger.info("Routed to accept.json");
			break;
		case "/AmatuerPhotoshop/user.json":
			uc.getEmployeeList(res);
			logger.info("Routed to user.json");
			break;
		case "/AmatuerPhotoshop/sorted.json":
			logger.info("Routed to sorted.json");
			rc.sortByEmployee(req, res);
		}
	}
}
