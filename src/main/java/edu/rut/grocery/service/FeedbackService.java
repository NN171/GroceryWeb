package edu.rut.grocery.service;

import edu.rut.grocery.dto.FeedbackDto;
import org.springframework.data.domain.Page;

public interface FeedbackService {

	Page<FeedbackDto> getFeedbacks(int page, int size);

	FeedbackDto getFeedback(Long id);

	String saveFeedback(FeedbackDto feedbackDto);

	String deleteFeedback(Long id);

	String updateFeedback(FeedbackDto FeedbackDto, Long id);
}
