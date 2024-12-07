package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.dto.FeedbackDto;
import edu.rut.grocery.service.FeedbackService;
import edu.rut.web.controllers.FeedbackController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.employee.CreateEmployeeViewModel;
import edu.rut.web.dto.feedback.CreateFeedbackForm;
import edu.rut.web.dto.feedback.CreateFeedbackViewModel;
import edu.rut.web.dto.feedback.EditFeedbackForm;
import edu.rut.web.dto.feedback.FeedbackSearchForm;
import edu.rut.web.dto.feedback.FeedbackViewModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/feedbacks")
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
	public String getFeedbacks(@ModelAttribute("form") FeedbackSearchForm form,
							   Model model) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		List<FeedbackDto> feedbacks = feedbackService.getFeedbacks(page, size);
		model.addAttribute("model", feedbacks);
		model.addAttribute()

		return "";
	}


	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	@PostMapping("/create")
	public String saveFeedback(@Valid @ModelAttribute("form") CreateFeedbackForm form,
							   BindingResult bindingResult,
							   Model model) {

		if (bindingResult.hasErrors()) {
			CreateFeedbackViewModel viewModel = new CreateFeedbackViewModel(
					createBaseViewModel("Create feedback")
			);
			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "feedback/feedback-create";
		}

		return "redirect:/feedbacks";
	}

	@Override
	public String deleteFeedback(@PathVariable Long id) {

		feedbackService.deleteFeedback(id);
		return "redirect:/feedbacks";
	}

	@Override
	public String updateFeedback(@PathVariable Long id,
								 @Valid @ModelAttribute("form") EditFeedbackForm form,
								 BindingResult bindingResult,
								 Model model) {
		return "";
	}

	@Override
	public String updateForm(Long id, Model model) {
		return "";
	}
}
