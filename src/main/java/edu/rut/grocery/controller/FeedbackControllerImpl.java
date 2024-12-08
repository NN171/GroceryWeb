package edu.rut.grocery.controller;

import edu.rut.grocery.dto.FeedbackDto;
import edu.rut.grocery.service.FeedbackService;
import edu.rut.web.controllers.FeedbackController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.customer.EditCustomerViewModel;
import edu.rut.web.dto.feedback.CreateFeedbackForm;
import edu.rut.web.dto.feedback.CreateFeedbackViewModel;
import edu.rut.web.dto.feedback.EditFeedbackForm;
import edu.rut.web.dto.feedback.EditFeedbackViewModel;
import edu.rut.web.dto.feedback.FeedbackListViewModel;
import edu.rut.web.dto.feedback.FeedbackSearchForm;
import edu.rut.web.dto.feedback.FeedbackViewModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@GetMapping("/")
	public String getFeedbacks(@ModelAttribute("form") FeedbackSearchForm form,
							   Model model) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		Page<FeedbackDto> feedbacks = feedbackService.getFeedbacks(page, size);
		List<FeedbackViewModel> FeedbackViewModel = feedbacks
				.stream()
				.map(f -> new FeedbackViewModel(
						f.id(),
						f.rating(),
						f.comment(),
						null))
				.toList();

		FeedbackListViewModel viewModel = new FeedbackListViewModel(
				createBaseViewModel("Feedback list"),
				FeedbackViewModel,
				page
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

		return "feedback/feedback-list";
	}


	@Override
	@GetMapping("/create")
	public String createForm(Model model) {

		CreateFeedbackViewModel viewModel = new CreateFeedbackViewModel(
				createBaseViewModel("Create feedback")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new CreateFeedbackForm(1, ""));

		return "feedback/feedback-create";
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

		FeedbackDto feedbackDto = new FeedbackDto(
				null,
				form.rating(),
				form.comment());

		feedbackService.saveFeedback(feedbackDto);

		return "redirect:/feedbacks";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteFeedback(@PathVariable Long id) {

		feedbackService.deleteFeedback(id);
		return "redirect:/feedbacks";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateFeedback(@PathVariable Long id,
								 @Valid @ModelAttribute("form") EditFeedbackForm form,
								 BindingResult bindingResult,
								 Model model) {

		if (bindingResult.hasErrors()) {
			EditCustomerViewModel viewModel = new EditCustomerViewModel(
					createBaseViewModel("Edit feedback")
			);

			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "feedback/feedback-edit";
		}

		FeedbackDto feedbackDto = new FeedbackDto(
				id,
				form.rating(),
				form.comment());

		feedbackService.updateFeedback(feedbackDto, id);

		return "redirect:/feedbacks";
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {

		FeedbackDto feedbackDto = feedbackService.getFeedback(id);

		EditFeedbackViewModel viewModel = new EditFeedbackViewModel(
				createBaseViewModel("Edit feedback"));

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditFeedbackForm(
				feedbackDto.id(),
				feedbackDto.rating(),
				feedbackDto.comment()
		));

		return "feedback/feedback-edit";
	}
}
