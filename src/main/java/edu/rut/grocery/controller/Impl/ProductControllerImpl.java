package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.ProductService;
import edu.rut.web.controllers.ProductController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.product.CreateProductForm;
import edu.rut.web.dto.product.EditProductForm;
import edu.rut.web.dto.product.ProductSearchForm;
import edu.rut.web.dto.product.ProductViewModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
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
	public String getProducts(@ModelAttribute("form") ProductSearchForm form, Model model) {
		return "";
	}

	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	public String saveProduct(@Valid @ModelAttribute("form") CreateProductForm form,
							  BindingResult bindingResult,
							  Model model) {
		return "";
	}

	@Override
	public String deleteProduct(@PathVariable Long id) {
		return "";
	}

	@Override
	public String updateProduct(@PathVariable Long id,
								@Valid @ModelAttribute("form") EditProductForm form,
								BindingResult bindingResult,
								Model model) {
		return "";
	}
}
