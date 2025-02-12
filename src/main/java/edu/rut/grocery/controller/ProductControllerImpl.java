package edu.rut.grocery.controller;

import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.service.ProductService;
import edu.rut.web.controllers.ProductController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.order.EditOrderViewModel;
import edu.rut.web.dto.product.CreateProductForm;
import edu.rut.web.dto.product.CreateProductViewModel;
import edu.rut.web.dto.product.EditProductForm;
import edu.rut.web.dto.product.EditProductViewModel;
import edu.rut.web.dto.product.ProductListViewModel;
import edu.rut.web.dto.product.ProductSearchForm;
import edu.rut.web.dto.product.ProductViewModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

@Controller
@RequestMapping("/products")
public class ProductControllerImpl implements ProductController {

	private final ProductService productService;
	private static final Logger LOG = LoggerFactory.getLogger(ProductControllerImpl.class);

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

		LOG.info("Получение списка продуктов");
		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		Page<ProductDto> products = productService.getProducts(page, size);
		List<ProductViewModel> ProductViewModel = products
				.stream()
				.map(p -> new ProductViewModel(
						p.getId(),
						p.getName(),
						p.getPrice(),
						p.getAmount(),
						p.getProdDate(),
						p.getExpiryDate(),
						p.getAvgRating(),
						null,
						null))
				.toList();

		ProductListViewModel viewModel = new ProductListViewModel(
				createBaseViewModel("Product list"),
				ProductViewModel,
				page,
				products.getTotalPages()
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

		return "product/product-list";
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {

		LOG.info("Форма создания продукта");
		CreateProductViewModel viewModel = new CreateProductViewModel(
				createBaseViewModel("Create product")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new CreateProductForm(
				"", 0L, 0, LocalDate.now(), LocalDate.now())
		);

		return "product/product-create";
	}

	@Override
	@PostMapping("/create")
	public String saveProduct(@Valid @ModelAttribute("form") CreateProductForm form,
							  BindingResult bindingResult,
							  Model model) {

		LOG.info("Создание продукта");
		if (bindingResult.hasErrors()) {

			CreateProductViewModel viewModel = new CreateProductViewModel(
					createBaseViewModel("Create product")
			);

			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			LOG.warn("Ошибка при создании продукта");

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
		LOG.info("Добавлен продукт {}", productDto);

		return "redirect:/products/";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {

		productService.deleteProduct(id);

		LOG.info("Удален продукт {}", id);
		return "redirect:/products/";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateProduct(@PathVariable Long id,
								@Valid @ModelAttribute("form") EditProductForm form,
								BindingResult bindingResult,
								Model model) {

		LOG.info("Обновление продукта {}", id);
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

		LOG.info("Обновлен продукт {}", productDto);
		return "redirect:/products/";
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {

		LOG.info("Форма обновления продукта");
		ProductDto product = productService.getProduct(id);

		EditProductViewModel viewModel = new EditProductViewModel(
				createBaseViewModel("Edit product")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditProductForm(
				product.getId(),
				product.getName(),
				product.getPrice(),
				product.getAmount(),
				product.getProdDate(),
				product.getExpiryDate()
		));

		return "product/product-edit";
	}
}
