package edu.rut.grocery.dto;

import java.io.Serializable;

public class FeedbackDto implements Serializable {
	private Long id;
	private int rating;
	private String comment;
	private Long productId;
	private String username;

	public FeedbackDto() {
	}

	public FeedbackDto(Long id, int rating, String comment, Long productId, String username) {
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.productId = productId;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
