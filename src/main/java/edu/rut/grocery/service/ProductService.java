package edu.rut.grocery.service;

import edu.rut.grocery.dto.FeedbackDto;
import edu.rut.grocery.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

	Page<ProductDto> getProducts(int page, int size);

	ProductDto getProduct(Long id);

	String saveProduct(ProductDto productDto);

	String deleteProduct(Long id);

	String updateProduct(ProductDto productDto, Long id);

	double calculateRating(Long id);
}
