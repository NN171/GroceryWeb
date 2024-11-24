package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.FeedbackService;
import edu.rut.web.controllers.model.FeedbackController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/feedbacks")
public class FeedbackControllerImpl implements FeedbackController {

	private final FeedbackService feedbackService;

	public FeedbackControllerImpl(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
}
