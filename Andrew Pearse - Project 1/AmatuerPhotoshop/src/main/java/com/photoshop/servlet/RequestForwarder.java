package com.photoshop.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.photoshop.controller.ReimbursementController;
import com.photoshop.controller.UserController;
import com.photoshop.util.SessionController;

public class RequestForwarder {

	UserController uc;
	ReimbursementController rc;
	SessionController sc;
	
	public RequestForwarder() {
		this(new UserController(), new ReimbursementController(), new SessionController());
	}
	
	public RequestForwarder(UserController uc, ReimbursementController rc, SessionController sc) {
		this.uc = uc;
		this.rc = rc;
		this.sc = sc;
	}
	
	public String routes(HttpServletRequest req) {
		switch(req.getRequestURI()) {
		case "/AmatuerPhotoshop/login.page":
			return uc.verifyUser(req);
		case "/AmatuerPhotoshop/employee.page":
			return "html/employee.html";
		case "/AmatuerPhotoshop/finance.page":
			return "html/finance manager.html";
		case "/AmatuerPhotoshop/request.page":
			return "html/new request.html";
		case "/AmatuerPhotoshop/create.page":
			return "html/employee.html";
		default:
			return "html/frontpage.html";
		}
	}
	
	public void data(HttpServletRequest req, HttpServletResponse res) {
		switch(req.getRequestURI()) {
		case "/AmatuerPhotoshop/allUserReimbursements.json":
			rc.sendUserReimbursementRequests(req, res);
			break;
		case "/AmatuerPhotoshop/reimb.json":
			rc.createNewReimbursement(req);
			break;
		case "/AmatuerPhotoshop/cancel.json":
			rc.cancelReimbursement(req);
			break;
		case "/AmatuerPhotoshop/allReimbursements.json":
			rc.sendAllPendingReimbursements(res);
			break;
		case "/AmatuerPhotoshop/reject.json":
			rc.rejectReimbursement(req, res);
			break;
		case "/AmatuerPhotoshop/accept.json":
			rc.acceptReimbursement(req, res);
			break;
		case "/AmatuerPhotoshop/user.json":
			uc.getEmployeeList(res);
			break;
		case "/AmatuerPhotoshop/sorted.json":
			rc.sortByEmployee(req, res);
		}
	}
}
