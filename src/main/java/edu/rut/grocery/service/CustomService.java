package edu.rut.grocery.service;

import edu.rut.grocery.dto.HighRatedDto;
import edu.rut.grocery.dto.ProductDto;

import java.util.List;

public interface CustomService {

	List<HighRatedDto> getHighRatedProducts();

	List<ProductDto> alwaysOrdering(Long id);
}
