package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.domain.Product;
import edu.rut.grocery.dto.FeedbackDto;
import edu.rut.grocery.repository.FeedbackRepository;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.repository.UserRepository;
import edu.rut.grocery.service.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private final ModelMapper modelMapper;
	private final FeedbackRepository feedbackRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper, ProductRepository productRepository, UserRepository userRepository) {
		this.feedbackRepository = feedbackRepository;
		this.modelMapper = modelMapper;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Cacheable("getFeedbacks")
	public Page<FeedbackDto> getFeedbacks(int page, int size, Long productId) {

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createDate").ascending());
		Page<Feedback> feedbacks = feedbackRepository.getFeedbacksByProductId(productId, pageable);

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
	@Cacheable(value = "getFeedback", key = "#id")
	public FeedbackDto getFeedback(Long id) {
		Feedback Feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

		return modelMapper.map(Feedback, FeedbackDto.class);
	}

	@Override
	@CacheEvict(value = "getFeedbacks", allEntries = true)
	public void saveFeedback(FeedbackDto feedbackDto) {
		Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
		feedback.setCreateDate(LocalDate.now());

		feedbackRepository.save(feedback);
	}

	@Override
	@CacheEvict(value = "getFeedbacks", allEntries = true)
	public String deleteFeedback(Long id) {
		boolean removed = feedbackRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Feedback not found");

		return "Success";
	}

	@Override
	@CacheEvict(value = "getFeedbacks", allEntries = true)
	public String updateFeedback(FeedbackDto feedbackDto, Long id) {
		Feedback feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
		modelMapper.map(feedbackDto, feedback);
		feedbackRepository.save(feedback);
		return "Feedback updated";
	}

	@Override
	public String getCustomerName(Long id) {
		Feedback feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

		return feedback.getCustomer().getFirstName();
	}

	@Override
	public String getFeedbackTime(FeedbackDto feedback) {

		return feedbackRepository.findById(feedback.getId())
				.orElseThrow(() -> new RuntimeException("Feedback not found")).getCreateDate().toString();
	}

	@Override
	@Cacheable(value = "getProduct", key = "#id")
	public Product getProduct(Long id) {
		Feedback feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

		return feedback.getProduct();
	}
}
