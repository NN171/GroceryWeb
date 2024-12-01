package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.OrderService;
import edu.rut.web.controllers.model.OrderController;
import edu.rut.web.dto.BaseViewModel;
import edu.rut.web.dto.OrderViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
	public String getOrders(OrderViewModel viewModel, Model model) {
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
	public String saveOrder(OrderViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String updateOrder(Long id, OrderViewModel viewModel, Model model) {
		return "";
	}
}
