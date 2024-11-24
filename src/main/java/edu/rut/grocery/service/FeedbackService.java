package edu.rut.grocery.service;

import edu.rut.grocery.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {

	List<FeedbackDto> getFeedbacks();

	FeedbackDto getFeedback(Long id);

	String saveFeedback(FeedbackDto feedbackDto);

	String deleteFeedback(Long id);

	String updateFeedback(FeedbackDto FeedbackDto, Long id);
}
