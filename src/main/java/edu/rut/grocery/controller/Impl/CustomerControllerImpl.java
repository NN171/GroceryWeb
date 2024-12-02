package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.CustomerService;
import edu.rut.web.controllers.CustomerController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.customer.CustomerViewModel;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

;

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
	public String getCustomers(@ModelAttribute("form") CustomerViewModel viewModel,
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
	public String saveCustomer(@Valid @ModelAttribute("form") CustomerViewModel viewModel,
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
								 @Valid @ModelAttribute("form") CustomerViewModel viewModel,
								 Model model) {
		return "";
	}
}
