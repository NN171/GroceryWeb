package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Product;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
	public List<ProductDto> getProducts(int page, int size) {

		Pageable pageable = PageRequest.of(page-1, size, Sort.by("expiryDate").descending());
		Page<Product> products = productRepository.findAll(pageable);

		return products.stream()
				.map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
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
	public String updateProduct(ProductDto productDto, Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		modelMapper.map(productDto, product);
		productRepository.save(product);
		return "Product updated";
	}
}
