package edu.rut.grocery.service;

import edu.rut.grocery.domain.Product;
import edu.rut.grocery.dto.FeedbackDto;
import org.springframework.data.domain.Page;

public interface FeedbackService {

	Page<FeedbackDto> getFeedbacks(int page, int size, Long productId);

	FeedbackDto getFeedback(Long id);

	void saveFeedback(FeedbackDto feedbackDto);

	String deleteFeedback(Long id);

	String updateFeedback(FeedbackDto FeedbackDto, Long id);

	String getCustomerName(Long id);

	String getFeedbackTime(FeedbackDto feedback);

	Product getProduct(Long id);
}
