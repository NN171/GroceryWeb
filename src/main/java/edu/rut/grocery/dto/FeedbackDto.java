package edu.rut.grocery.dto;

public record FeedbackDto(
		Long id,
		int rating,
		String comment
) {
}
