package edu.rut.grocery.controller;

import edu.rut.grocery.dto.CustomerDto;
import edu.rut.grocery.service.CustomerService;
import edu.rut.web.controllers.CustomerController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.customer.CreateCustomerForm;
import edu.rut.web.dto.customer.CreateCustomerViewModel;
import edu.rut.web.dto.customer.CustomerListViewModel;
import edu.rut.web.dto.customer.CustomerSearchForm;
import edu.rut.web.dto.customer.CustomerViewModel;
import edu.rut.web.dto.customer.EditCustomerForm;
import edu.rut.web.dto.customer.EditCustomerViewModel;
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
@RequestMapping("/customers")
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
	public String getCustomers(@ModelAttribute("form") CustomerSearchForm form,
							   Model model) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		Page<CustomerDto> customers = customerService.getCustomers(page, size);
		List<CustomerViewModel> customerViewModelList = customers
				.stream()
				.map(c -> new CustomerViewModel(
						c.getId(),
						c.getFirstName(),
						c.getLastName(),
						c.getPhoneNumber(),
						c.getDiscount(),
						c.getOrdersAmount(),
						null,
						null))
				.toList();

		CustomerListViewModel viewModel = new CustomerListViewModel(
				createBaseViewModel("Customer list"),
				customerViewModelList,
				page,
				customers.getTotalPages()
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

		return "customer/customer-list";
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {

		CreateCustomerViewModel viewModel = new CreateCustomerViewModel(
				createBaseViewModel("Edit customer"));

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new CreateCustomerForm("", "", ""));
		return "customer/customer-create";
	}

	@PostMapping("/create")
	public String saveCustomer(@Valid @ModelAttribute("form") CreateCustomerForm form,
							   BindingResult bindingResult,
							   Model model) {

		if (bindingResult.hasErrors()) {
			CreateCustomerViewModel viewModel = new CreateCustomerViewModel(
					createBaseViewModel("Create customer")
			);
			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "customer/customer-create";
		}

		CustomerDto customerDto = new CustomerDto(
				null,
				form.firstName(),
				form.lastName(),
				form.phone(),
				null,
				0);

		customerService.saveCustomer(customerDto);
		return "redirect:/customers";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable Long id) {

		customerService.deleteCustomer(id);
		return "redirect:/customers/";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateCustomer(@PathVariable Long id,
								 @Valid @ModelAttribute("form") EditCustomerForm form,
								 BindingResult bindingResult,
								 Model model) {

		if (bindingResult.hasErrors()) {
			EditCustomerViewModel viewModel = new EditCustomerViewModel(
					createBaseViewModel("Edit customer")
			);
			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "customer/customer-edit";
		}

		CustomerDto customerDto = new CustomerDto(
				id,
				form.firstName(),
				form.lastName(),
				form.phone(),
				customerService.calculateOrders(id),
				customerService.calculateDiscount(id));

		customerService.updateCustomer(customerDto, id);
		return "redirect:/customers/";
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		CustomerDto customer = customerService.getCustomer(id);
		EditCustomerViewModel viewModel = new EditCustomerViewModel(
				createBaseViewModel("Edit customer"));

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditCustomerForm(
				customer.getId(),
				customer.getFirstName(),
				customer.getLastName(),
				customer.getPhoneNumber()));

		return "customer/customer-edit";
	}
}
