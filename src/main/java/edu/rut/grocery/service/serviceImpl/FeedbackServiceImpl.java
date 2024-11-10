package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.repository.FeedbackRepository;
import edu.rut.grocery.service.FeedbackService;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private final FeedbackRepository feedbackRepository;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}
}
