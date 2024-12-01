package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.FeedbackService;
import edu.rut.web.controllers.model.FeedbackController;
import edu.rut.web.dto.BaseViewModel;
import edu.rut.web.dto.FeedbackViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/feedbacks")
public class FeedbackControllerImpl implements FeedbackController {

	private final FeedbackService feedbackService;

	public FeedbackControllerImpl(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}

	@Override
	public String getFeedbacks(FeedbackViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String getFeedback(Long id, Model model) {
		return "";
	}

	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	public String saveFeedback(FeedbackViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String deleteFeedback(Long id) {
		return "";
	}

	@Override
	public String updateFeedback(Long id, FeedbackViewModel viewModel, Model model) {
		return "";
	}
}
