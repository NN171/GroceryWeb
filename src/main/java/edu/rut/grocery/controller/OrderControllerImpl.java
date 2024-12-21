package edu.rut.grocery.controller;

import edu.rut.grocery.domain.Status;
import edu.rut.grocery.dto.OrderDto;
import edu.rut.grocery.dto.OrderProductDto;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.service.BasketService;
import edu.rut.grocery.service.OrderService;
import edu.rut.web.controllers.OrderController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.order.CreateOrderForm;
import edu.rut.web.dto.order.CreateOrderViewModel;
import edu.rut.web.dto.order.EditOrderForm;
import edu.rut.web.dto.order.EditOrderViewModel;
import edu.rut.web.dto.order.OrderListViewModel;
import edu.rut.web.dto.order.OrderSearchForm;
import edu.rut.web.dto.order.OrderViewModel;
import edu.rut.web.dto.productOrder.ProductOrderViewModel;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderControllerImpl implements OrderController {

	private final OrderService orderService;
	private final BasketService basketService;

	public OrderControllerImpl(OrderService orderService, BasketService basketService) {
		this.orderService = orderService;
		this.basketService = basketService;
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}

	@Override
	@GetMapping("/")
	public String getOrders(@ModelAttribute("form") OrderSearchForm form,
							Model model) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		Page<OrderDto> orders = orderService.getOrders(page, size);

		List<OrderViewModel> orderViewModels = orders
				.stream()
				.map(o -> {
					List<ProductOrderViewModel> productViewModels = o.getProducts().stream()
							.map(p -> new ProductOrderViewModel(
									p.getProductId(),
									p.getProductName(),
									p.getQuantity(),
									p.getPrice()
							))
							.toList();

					return new OrderViewModel(
							o.getId(),
							o.getStatus(),
							o.getPrice(),
							productViewModels
					);
				})
				.toList();

		OrderListViewModel viewModel = new OrderListViewModel(
				createBaseViewModel("Order list"),
				orderViewModels,
				page,
				orders.getTotalPages()
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

		return "order/order-list";
	}

//	@Override
//	@GetMapping("/order")
//	public String getOrder(@RequestParam Long id,
//						   Model model) {
//
//		OrderDto order = orderService.getOrder(id);
//
//		List<ProductOrderViewModel> products = order.products().stream()
//				.map(po -> new ProductOrderViewModel(
//						id,
//						po.productName(),
//						po.quantity(),
//						po.price()
//				)).toList();
//
//		OrderViewModel viewModel = new OrderViewModel(
//				order.id(),
//				order.status(),
//				order.price(),
//				products
//		);
//
//		model.addAttribute("model", viewModel);
//
//		return "basket/basket-view";
//	}

	@PostMapping("/add")
	public String addProductToCart(@RequestParam Long customerId,
								   @RequestParam Long productId,
								   @RequestParam int quantity,
								   Model model) {
		try {
			basketService.addProduct(customerId, productId, quantity);

			model.addAttribute("message", "Product added to cart successfully!");

			return "redirect:/cart";
		} catch (EntityNotFoundException e) {
			model.addAttribute("error", "Product not found: " + e.getMessage());
			return "error-page";
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", "Invalid quantity: " + e.getMessage());
			return "error-page";
		}
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {

		CreateOrderViewModel viewModel = new CreateOrderViewModel(
				createBaseViewModel("Create order")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new CreateOrderForm(
				0L,
				0L,
				new ArrayList<>()
		));

		return "order/order-create";
	}

	@Override
	@PostMapping("/create")
	public String saveOrder(@Valid @ModelAttribute("form") CreateOrderForm form,
							BindingResult bindingResult,
							Model model) {

		if (bindingResult.hasErrors()) {

			CreateOrderViewModel viewModel = new CreateOrderViewModel(
					createBaseViewModel("Create order")
			);

			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "order/order-create";
		}

		OrderDto orderDto = new OrderDto(
				null,
				"Создан",
				null,
				form.productList().stream()
						.map(p -> new OrderProductDto(p.productId(), p.productName(), p.quantity(), p.price()))
						.toList());

		orderService.saveOrder(orderDto);

		return "redirect:/orders/";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateOrder(@PathVariable Long id,
							  @Valid @ModelAttribute("form") EditOrderForm form,
							  BindingResult bindingResult,
							  Model model) {

		if (bindingResult.hasErrors()) {

			EditOrderViewModel viewModel = new EditOrderViewModel(
					createBaseViewModel("editViewModel")
			);

			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "order/order-edit";
		}

		return "redirect:/orders/";
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {

		OrderDto order = orderService.getOrder(id);

		EditOrderViewModel viewModel = new EditOrderViewModel(
				createBaseViewModel("Edit order")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditOrderForm(
				order.getId(), order.getStatus(), order.getPrice()
		));

		return "order/order-edit";
	}
}
