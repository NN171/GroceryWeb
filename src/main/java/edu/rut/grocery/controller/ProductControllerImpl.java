package edu.rut.grocery.controller;

import edu.rut.grocery.dto.CustomerDto;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.service.ProductService;
import edu.rut.web.controllers.ProductController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.customer.EditCustomerForm;
import edu.rut.web.dto.customer.EditCustomerViewModel;
import edu.rut.web.dto.order.EditOrderViewModel;
import edu.rut.web.dto.product.CreateProductForm;
import edu.rut.web.dto.product.CreateProductViewModel;
import edu.rut.web.dto.product.EditProductForm;
import edu.rut.web.dto.product.EditProductViewModel;
import edu.rut.web.dto.product.ProductListViewModel;
import edu.rut.web.dto.product.ProductSearchForm;
import edu.rut.web.dto.product.ProductViewModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
	@GetMapping("/")
	public String getProducts(@ModelAttribute("form") ProductSearchForm form, Model model) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		Page<ProductDto> products = productService.getProducts(page, size);
		List<ProductViewModel> ProductViewModel = products
				.stream()
				.map(p -> new ProductViewModel(
						p.id(),
						p.name(),
						p.price(),
						p.amount(),
						p.prodDate(),
						p.expiryDate(),
						p.avgRating(),
						null,
						null))
				.toList();

		ProductListViewModel viewModel = new ProductListViewModel(
				createBaseViewModel("Product list"),
				ProductViewModel,
				page
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

		return "redirect:/products";
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {

		CreateProductViewModel viewModel = new CreateProductViewModel(
				createBaseViewModel("Create product")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new CreateProductForm(
				"", 0L, 0, "", "")
		);

		return "product/product-create";
	}

	@Override
	@PostMapping("/create")
	public String saveProduct(@Valid @ModelAttribute("form") CreateProductForm form,
							  BindingResult bindingResult,
							  Model model) {

		if (bindingResult.hasErrors()) {

			CreateProductViewModel viewModel = new CreateProductViewModel(
					createBaseViewModel("Create product")
			);

			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "product/product-create";
		}

		ProductDto productDto = new ProductDto(
				null,
				form.name(),
				form.price(),
				form.amount(),
				form.creationDate(),
				form.expiryDate(),
				null
		);

		productService.saveProduct(productDto);

		return "redirect:/products";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {

		productService.deleteProduct(id);

		return "redirect:/products";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateProduct(@PathVariable Long id,
								@Valid @ModelAttribute("form") EditProductForm form,
								BindingResult bindingResult,
								Model model) {

		if (bindingResult.hasErrors()) {
			EditOrderViewModel viewModel = new EditOrderViewModel(
					createBaseViewModel("Edit product")
			);
			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "customer/customer-edit";
		}

		ProductDto productDto = new ProductDto(
				id,
				form.name(),
				form.price(),
				form.amount(),
				form.creationDate(),
				form.expiryDate(),
				productService.calculateRating(id));

		productService.updateProduct(productDto, id);

		return "redirect:/products";
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(Long id, Model model) {

		ProductDto product = productService.getProduct(id);

		EditProductViewModel viewModel = new EditProductViewModel(
				createBaseViewModel("Edit product")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditProductForm(
				product.id(),
				product.name(),
				product.price(),
				product.amount(),
				product.prodDate(),
				product.expiryDate()
		));

		return "product/product-create";
	}
}
