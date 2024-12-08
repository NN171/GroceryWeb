package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.domain.Product;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

	private final ModelMapper modelMapper;
	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Page<ProductDto> getProducts(int page, int size) {

		Pageable pageable = PageRequest.of(page-1, size, Sort.by("expiryDate").descending());
		Page<Product> products = productRepository.findAll(pageable);

		return new PageImpl<>(
				products.getContent().stream()
						.map(element -> modelMapper.map(element, ProductDto.class))
						.collect(Collectors.toList()
						),
				products.getPageable(),
				products.getTotalPages()
		);
	}

	@Override
	public ProductDto getProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		return modelMapper.map(product, ProductDto.class);
	}

	@Override
	public String saveProduct(ProductDto productDto) {

		Product product = modelMapper.map(productDto, Product.class);
		productRepository.save(product);

		return "Product saved";
	}

	@Override
	public String deleteProduct(Long id) {

		boolean removed = productRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Product not found");

		return "Success";
	}

	@Override
	public double calculateRating(Long id) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		Set<Feedback> feedbacks = product.getFeedbacks();
		double rating = 0;

		for (Feedback feedback : feedbacks) {
			rating += feedback.getRating();
		}

		return rating/feedbacks.size();
	}

	@Override
	public String updateProduct(ProductDto productDto, Long id) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		modelMapper.map(productDto, product);
		productRepository.save(product);
		return "Product updated";
	}
}
