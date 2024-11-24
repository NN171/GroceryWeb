package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.ProductService;
import edu.rut.web.controllers.model.ProductController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
public class ProductControllerImpl implements ProductController {

	private final ProductService productService;


	public ProductControllerImpl(ProductService productService) {
		this.productService = productService;
	}
}
