package edu.rut.grocery.service;

import edu.rut.grocery.dto.ProductDto;

import java.util.List;

public interface ProductService {

	List<ProductDto> getProducts();

	ProductDto getProduct(Long id);

	String saveProduct(ProductDto productDto);

	String deleteProduct(Long id);

	String updateProduct(ProductDto productDto, Long id);
}
