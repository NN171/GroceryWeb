package edu.rut.grocery.service;

import edu.rut.grocery.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {

	Page<ProductDto> getProducts(int page, int size);

	ProductDto getProduct(Long id);

	String saveProduct(ProductDto productDto);

	String deleteProduct(Long id);

	String updateProduct(ProductDto productDto, Long id);

	double calculateRating(Long id);
}
