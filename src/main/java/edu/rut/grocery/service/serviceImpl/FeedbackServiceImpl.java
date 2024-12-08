package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.dto.FeedbackDto;
import edu.rut.grocery.repository.FeedbackRepository;
import edu.rut.grocery.service.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private final ModelMapper modelMapper;
	private final FeedbackRepository feedbackRepository;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper) {
		this.feedbackRepository = feedbackRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Page<FeedbackDto> getFeedbacks(int page, int size) {

		Pageable pageable = PageRequest.of(page-1, size, Sort.by("createDate").ascending());
		Page<Feedback> feedbacks = feedbackRepository.findAll(pageable);

		return new PageImpl<>(
				feedbacks.getContent().stream()
						.map(element -> modelMapper.map(element, FeedbackDto.class))
						.collect(Collectors.toList()
						),
				feedbacks.getPageable(),
				feedbacks.getTotalPages()
		);
	}

	@Override
	public FeedbackDto getFeedback(Long id) {
		Feedback Feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

		return modelMapper.map(Feedback, FeedbackDto.class);
	}

	@Override
	public String saveFeedback(FeedbackDto feedbackDto) {
		Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
		feedbackRepository.save(feedback);

		return "Feedback saved";
	}

	@Override
	public String deleteFeedback(Long id) {
		boolean removed = feedbackRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Feedback not found");

		return "Success";
	}

	@Override
	public String updateFeedback(FeedbackDto FeedbackDto, Long id) {
		Feedback Feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
		modelMapper.map(FeedbackDto, Feedback);
		feedbackRepository.save(Feedback);
		return "Feedback updated";
	}
}
