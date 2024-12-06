package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.OrderService;
import edu.rut.web.controllers.OrderController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.order.OrderViewModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/orders")
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
	public String getOrders(@ModelAttribute("form") OrderViewModel viewModel,
							Model model) {
		return "";
	}

	@Override
	public String getOrder(Long id, Model model) {
		return "";
	}

	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	public String saveOrder(@Valid @ModelAttribute("form") OrderViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String updateOrder(Long id,
							  @Valid @ModelAttribute("form") OrderViewModel viewModel,
							  Model model) {
		return "";
	}
}
