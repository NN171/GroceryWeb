package edu.rut.grocery.controller;

import edu.rut.grocery.dto.OrderDto;
import edu.rut.grocery.dto.OrderProductDto;
import edu.rut.grocery.service.AuthService;
import edu.rut.grocery.service.BasketService;
import edu.rut.grocery.service.OrderService;
import edu.rut.web.controllers.OrderController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.order.CreateOrderForm;
import edu.rut.web.dto.order.CreateOrderViewModel;
import edu.rut.web.dto.order.OrderListViewModel;
import edu.rut.web.dto.order.OrderSearchForm;
import edu.rut.web.dto.order.OrderViewModel;
import edu.rut.web.dto.productOrder.ProductOrderViewModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderControllerImpl implements OrderController {

	private final OrderService orderService;
	private final BasketService basketService;
	private final AuthService authService;

	public OrderControllerImpl(OrderService orderService, BasketService basketService, AuthService authService) {
		this.orderService = orderService;
		this.basketService = basketService;
		this.authService = authService;
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

	@Override
	@PostMapping("/add")
	public String addProduct(@RequestParam Long productId,
							 @RequestParam int quantity,
							 Model model,
							 @AuthenticationPrincipal UserDetails userDetails) {

		Long customerId = authService.getUser(userDetails.getUsername()).getCustomer().getId();

		basketService.addProduct(customerId, productId, quantity);

//		List<ProductOrderViewModel> products = order.getProducts().stream()
//				.map(po -> new ProductOrderViewModel(
//						productId,
//						po.getProductName(),
//						po.getQuantity(),
//						po.getPrice()
//				)).toList();
//
//		OrderViewModel viewModel = new OrderViewModel(
//				order.getId(),
//				order.getStatus(),
//				order.getPrice(),
//				products
//		);
//
//		model.addAttribute("model", viewModel);

		return "redirect:/products/";
	}

	@Override
	@GetMapping("/order")
	public String getOrder(Model model,
						   @AuthenticationPrincipal UserDetails userDetails) {

		Long customerId = authService.getUser(userDetails.getUsername()).getCustomer().getId();

		OrderDto order = orderService.getActiveOrder(customerId);

		List<ProductOrderViewModel> products = order.getProducts().stream()
				.map(po -> new ProductOrderViewModel(
						po.getProductId(),
						po.getProductName(),
						po.getQuantity(),
						po.getPrice()
				)).toList();

		OrderViewModel viewModel = new OrderViewModel(
				order.getId(),
				order.getStatus(),
				order.getPrice(),
				products
		);

		model.addAttribute("model", viewModel);

		return "basket/basket-view";
	}

	@PostMapping("/delete")
	public String removeProduct(@RequestParam Long productId,
								@RequestParam int quantity,
								Model model) {

		return "";
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
						.collect(Collectors.toSet()));

		orderService.saveOrder(orderDto);

		return "redirect:/orders/";
	}
}
