package com.photoshop.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoshop.model.Reimbursement;
import com.photoshop.model.ReimbursementStatus;
import com.photoshop.service.ReimbursementService;
import com.photoshop.util.SessionController;

public class ReimbursementController {

	private ReimbursementService rs;
	private SessionController sc;
	
	private static Logger logger = Logger.getLogger(ReimbursementController.class);
	
	public ReimbursementController(ReimbursementService rs, SessionController sc) {
		super();
		this.rs = rs;
		this.sc = sc;
	}
	
	public ReimbursementController() {
		this(new ReimbursementService(), new SessionController());
	}
	
	public boolean sendAllPendingReimbursements(HttpServletResponse res) {
		res.setContentType("text/json");
		List<Reimbursement> reimbursements = rs.findAllPending();
		
		try {
			res.getWriter().println(new ObjectMapper().writeValueAsString(reimbursements));
			logger.info("Successfully loaded all pending reimbursements");
		} catch (IOException e) {
			logger.warn("Problem loading all pending reimbursements: ", e);
			return false;
		}
		
		return true;
	}
	
	public boolean sendUserReimbursementRequests(HttpServletRequest req, HttpServletResponse res) {
		int id = sc.getSessionUser(req).getId();
		res.setContentType("text/json");
		List<Reimbursement> reimbursements = rs.findAllByUser(id);
		
		try {
			res.getWriter().println(new ObjectMapper().writeValueAsString(reimbursements));
			logger.info("Successfully loaded current users reimbursement requests");
		} catch (IOException e) {
			logger.warn("Could not load current users reimbursement requests: ", e);
			return false;
		}
		
		return true;
	}
	
	public boolean createNewReimbursement(HttpServletRequest req) {
		Reimbursement reimb = null;
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(req.getInputStream());
			reimb = new Reimbursement(Float.valueOf(jsonNode.get("amount").asText()), jsonNode.get("description").asText(), jsonNode.get("type").asInt());
			reimb.setAuthor(sc.getSessionUser(req));

			rs.request(reimb);
			logger.info("Sent reimbursement request to be created");
		} catch (IOException e) {
			logger.warn("Problem sending reimbursement request", e);
			return false;
		}
		
		return true;
	}
	
	public int cancelReimbursement(HttpServletRequest req) {
		try {
			JsonNode jNode = new ObjectMapper().readTree(req.getInputStream());
			int num = rs.delete(jNode.get("cid").asInt());
			logger.info("Sent reimbursement to cancel");
			
			return num;
		} catch (IOException e) {
			logger.warn("Problem sending cancel request: ", e);
		}
		
		return -1;
	}
	
	public boolean rejectReimbursement(HttpServletRequest req, HttpServletResponse res) {
		Reimbursement reimb = null;
		try {
			JsonNode jNode = new ObjectMapper().readTree(req.getInputStream());
			reimb = rs.findById(jNode.get("cid").asInt());
			reimb.setResolver(sc.getSessionUser(req));
			reimb.setDateResolved(LocalDateTime.now());
			reimb.setStatus(new ReimbursementStatus(3, null));
			
			rs.updateRequest(reimb);
			logger.info("Sent request to be rejected");
		} catch (IOException e) {
			logger.warn("Problem sending update request: ", e);
			return false;
		}
		
		return true;
	}
	
	public boolean acceptReimbursement(HttpServletRequest req, HttpServletResponse res) {
		Reimbursement reimb = null;
		try {
			JsonNode jNode = new ObjectMapper().readTree(req.getInputStream());
			reimb = rs.findById(jNode.get("cid").asInt());
			reimb.setResolver(sc.getSessionUser(req));
			reimb.setDateResolved(LocalDateTime.now());
			reimb.setStatus(new ReimbursementStatus(2, null));
			
			rs.updateRequest(reimb);
			logger.info("Sent request to be accepted");
		} catch (IOException e) {
			logger.warn("Problem sending update request: ", e);
			return false;
		}
		
		return true;
	}
	
	public boolean sortByEmployee(HttpServletRequest req, HttpServletResponse res) {
		List<Reimbursement> reimbursements = null;
		res.setContentType("text/json");
		try {
			JsonNode jNode = new ObjectMapper().readTree(req.getInputStream());
			
			if(jNode.get("uid").asInt() == 0) {
				reimbursements = rs.findAllPending();
			}
			else {
				reimbursements = rs.sortByEmployee(jNode.get("uid").asInt());
			}
			
			res.getWriter().println(new ObjectMapper().writeValueAsString(reimbursements));
			logger.info("Sorted reimbursements by employee id: " + jNode.get("uid").asInt());
		} catch (IOException e) {
			logger.warn("Problem sorting reimbursements by specific user: ", e);
			return false;
		}
		
		return true;
	}
}
