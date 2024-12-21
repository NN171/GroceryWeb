package edu.rut.grocery.controller;

import edu.rut.grocery.domain.User;
import edu.rut.grocery.dto.FeedbackDto;
import edu.rut.grocery.service.AuthService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackControllerImpl implements FeedbackController {

	private final FeedbackService feedbackService;
	private final AuthService authService;

	public FeedbackControllerImpl(FeedbackService feedbackService, AuthService authService) {
		this.feedbackService = feedbackService;
		this.authService = authService;
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}

	@Override
	@GetMapping("/{productId}")
	public String getFeedbacks(@ModelAttribute("form") FeedbackSearchForm form,
							   Model model,
							   @PathVariable Long productId) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		Page<FeedbackDto> feedbacks = feedbackService.getFeedbacks(page, size, productId);
		List<FeedbackViewModel> FeedbackViewModel = feedbacks
				.stream()
				.map(f -> new FeedbackViewModel(
						f.getId(),
						f.getRating(),
						f.getComment(),
						feedbackService.getFeedbackTime(f),
						productId,
						feedbackService.getCustomerName(f.getId())))
				.toList();

		FeedbackListViewModel viewModel = new FeedbackListViewModel(
				createBaseViewModel("Feedback list"),
				FeedbackViewModel,
				feedbackService.getProduct(feedbacks.getContent().getFirst().getId()).getName(),
				page,
				feedbacks.getTotalPages()
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
		model.addAttribute("form", new CreateFeedbackForm(1L, 1, ""));

		return "feedback/feedback-create";
	}

	@Override
	@PostMapping("/create")
	public String saveFeedback(@Valid @ModelAttribute("form") CreateFeedbackForm form,
							   BindingResult bindingResult,
							   Model model,
							   @AuthenticationPrincipal UserDetails userDetails) {

		if (bindingResult.hasErrors()) {
			CreateFeedbackViewModel viewModel = new CreateFeedbackViewModel(
					createBaseViewModel("Create feedback")
			);
			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "feedback/feedback-create";
		}

		User user = authService.getUser(userDetails.getUsername());

		FeedbackDto feedbackDto = new FeedbackDto(
				null,
				form.rating(),
				form.comment(),
				form.productId(),
				user.getCustomer().getId());

		feedbackService.saveFeedback(feedbackDto);

		return "redirect:/feedbacks/" + feedbackDto.getId();
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
								 Model model,
								 @AuthenticationPrincipal UserDetails userDetails) {

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
				form.comment(),
				feedbackService.getProduct(id).getId(),
				authService.getUser(userDetails.getUsername()).getCustomer().getId());

		feedbackService.updateFeedback(feedbackDto, id);

		return "redirect:/feedbacks/" + id;
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {

		FeedbackDto feedbackDto = feedbackService.getFeedback(id);

		EditFeedbackViewModel viewModel = new EditFeedbackViewModel(
				createBaseViewModel("Edit feedback"));

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditFeedbackForm(
				feedbackDto.getId(),
				feedbackDto.getRating(),
				feedbackDto.getComment(),
				feedbackDto.getProductId(),
				feedbackDto.getCustomerId()
		));

		return "feedback/feedback-edit";
	}
}
