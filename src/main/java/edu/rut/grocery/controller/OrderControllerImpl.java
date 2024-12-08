package edu.rut.grocery.controller;

import edu.rut.grocery.dto.OrderDto;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderControllerImpl implements OrderController {

	private final OrderService orderService;


	public OrderControllerImpl(OrderService orderService) {
		this.orderService = orderService;
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
		List<OrderViewModel> OrderViewModel = orders
				.stream()
				.map(o -> new OrderViewModel(
						o.id(),
						o.price(),
						o.customer(),
						o.employee(),
						null))
				.toList();

		OrderListViewModel viewModel = new OrderListViewModel(
				createBaseViewModel("Order list"),
				OrderViewModel,
				page
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

		return "order/order-list";
	}

	@Override
	@GetMapping("/{id}")
	public String getOrder(@PathVariable Long id,
						   Model model) {

		OrderDto order = orderService.getOrder(id); //TODO

		return "order/order";
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
				"created",
				null,
				form.customerId(),
				form.employeeId(),
				form.productList().stream()
						.map(p -> new OrderDto.OrderProductDto(p.productId(), p.quantity(), p.price()))
						.toList());

		orderService.saveOrder(orderDto);

		return "redirect:/orders";
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

		return "redirect:/orders";
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
				order.id(), order.status(), order.price()
		));

		return "order/order-edit";
	}
}
