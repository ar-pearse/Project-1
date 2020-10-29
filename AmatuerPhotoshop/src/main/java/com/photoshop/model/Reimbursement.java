package com.photoshop.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reimbursement {

	private int id;
	private float amount;
	private LocalDateTime dateSubmitted;
	private LocalDateTime dateResolved;
	private String description;
	private User author;
	private User resolver;
	private ReimbursementStatus status;
	private ReimbursementType type;

	public Reimbursement() {
	}
	
	public Reimbursement(float amount, String description, int typeId) {
		this(0, amount, Timestamp.valueOf(LocalDateTime.now()), 
				null, description, null, null, 
				new ReimbursementStatus(1, null), 
				new ReimbursementType(typeId, null));
	}
	
	public Reimbursement(int id, float amount, Timestamp dateSubmitted, Timestamp dateResolved, String description,
			User author, User resolver, ReimbursementStatus status, ReimbursementType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.dateSubmitted = dateSubmitted.toLocalDateTime();
		
		if (dateResolved == null)
			this.dateResolved = null;
		else
			this.dateResolved = dateResolved.toLocalDateTime();
		
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public LocalDateTime getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(LocalDateTime dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public LocalDateTime getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(LocalDateTime dateResolved) {
		this.dateResolved = dateResolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public ReimbursementStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatus status) {
		this.status = status;
	}

	public ReimbursementType getType() {
		return type;
	}

	public void setType(ReimbursementType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", dateSubmitted=" + dateSubmitted + ", dateResolved="
				+ dateResolved + ", description=" + description + ", author=" + author
				+ ", resolver=" + resolver + ", status=" + status + ", type=" + type + "]";
	}

}
