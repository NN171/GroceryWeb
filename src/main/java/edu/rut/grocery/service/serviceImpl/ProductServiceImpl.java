package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

}
