package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.ProductService;
import edu.rut.web.controllers.ProductController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.product.ProductViewModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/products")
public class ProductControllerImpl implements ProductController {

	private final ProductService productService;


	public ProductControllerImpl(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}

	@Override
	public String getProducts(@ModelAttribute("form")ProductViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String getProduct(Long id, Model model) {
		return "";
	}

	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	public String saveProduct(@Valid @ModelAttribute("form") ProductViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String deleteProduct(Long id) {
		return "";
	}

	@Override
	public String updateProduct(Long id,
								@Valid @ModelAttribute("form") ProductViewModel viewModel,
								Model model) {
		return "";
	}
}
