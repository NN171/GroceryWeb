package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.CustomerService;
import edu.rut.web.controllers.model.CustomerController;
import edu.rut.web.dto.BaseViewModel;
import edu.rut.web.dto.CustomerViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerControllerImpl implements CustomerController {

	private final CustomerService customerService;

	public CustomerControllerImpl(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}

	@Override
	@GetMapping("/")
	public String getCustomers(@ModelAttribute CustomerViewModel viewModel,
						Model model) {
		return "";
	}

	@Override
	@GetMapping("/{id}")
	public String getCustomer(@PathVariable Long id,
					   Model model) {
		return "";
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {
		return "";
	}

	@PostMapping("/create")
	public String saveCustomer(@ModelAttribute CustomerViewModel viewModel,
						Model model) {
		return "";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable Long id) {
		return "";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateCustomer(@PathVariable Long id,
						  @ModelAttribute CustomerViewModel viewModel,
						  Model model) {
		return "";
	}
}
