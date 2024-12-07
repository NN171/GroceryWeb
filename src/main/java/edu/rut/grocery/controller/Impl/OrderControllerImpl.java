package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.OrderService;
import edu.rut.web.controllers.OrderController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.order.CreateOrderForm;
import edu.rut.web.dto.order.EditOrderForm;
import edu.rut.web.dto.order.OrderSearchForm;
import edu.rut.web.dto.order.OrderViewModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String getOrders(@ModelAttribute("form") OrderSearchForm form,
							Model model) {
		return "";
	}

	@Override
	public String getOrder(@PathVariable Long id,
						   Model model) {
		return "";
	}

	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	public String saveOrder(@Valid @ModelAttribute("form") CreateOrderForm form,
							BindingResult bindingResult,
							Model model) {
		return "";
	}

	@Override
	public String updateOrder(@PathVariable Long id,
							  @Valid @ModelAttribute("form") EditOrderForm form,
							  BindingResult bindingResult,
							  Model model) {
		return "";
	}
}
