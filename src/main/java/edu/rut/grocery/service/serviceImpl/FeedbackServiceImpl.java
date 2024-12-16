package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Customer;
import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.domain.Product;
import edu.rut.grocery.dto.FeedbackDto;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.repository.FeedbackRepository;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private final ModelMapper modelMapper;
	private final FeedbackRepository feedbackRepository;
	private final ProductRepository productRepository;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper, ProductRepository productRepository) {
		this.feedbackRepository = feedbackRepository;
		this.modelMapper = modelMapper;
		this.productRepository = productRepository;
	}

	@Override
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
	public FeedbackDto getFeedback(Long id) {
		Feedback Feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

		return modelMapper.map(Feedback, FeedbackDto.class);
	}

	@Override
	public String saveFeedback(FeedbackDto feedbackDto) {
		Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
		feedback.setCreateDate(LocalDateTime.now());

		Product product = productRepository.findById(feedbackDto.id())
						.orElseThrow(() -> new RuntimeException("Product not found"));



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
	public String updateFeedback(FeedbackDto feedbackDto, Long id) {
		Feedback feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
		modelMapper.map(feedbackDto, feedback);
		feedbackRepository.save(feedback);
		return "Feedback updated";
	}

	@Override
	public String getCustomerName(Long id) {
		Customer customer = feedbackRepository.getCustomerById(id);

		return customer.getFirstName();
	}

	@Override
	public String getFeedbackTime(FeedbackDto feedback) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime dateTime = feedbackRepository.findById(feedback.id())
				.orElseThrow(() -> new RuntimeException("Feedback not found")).getCreateDate();

		return dateTime.format(formatter);
	}

	@Override
	public Product getProduct(Long id) {
		Product product = feedbackRepository.getProductById(id);

		return product;
	}
}
