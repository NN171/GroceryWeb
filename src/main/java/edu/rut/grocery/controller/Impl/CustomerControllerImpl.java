package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.CustomerService;
import edu.rut.web.controllers.model.CustomerController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerControllerImpl implements CustomerController {

	private final CustomerService customerService;

	public CustomerControllerImpl(CustomerService customerService) {
		this.customerService = customerService;
	}

}
