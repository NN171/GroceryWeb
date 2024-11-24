package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.EmployeeService;
import edu.rut.web.controllers.model.EmployeeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeControllerImpl implements EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeControllerImpl(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
}
