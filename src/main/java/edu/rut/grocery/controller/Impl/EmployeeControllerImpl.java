package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.EmployeeService;
import edu.rut.web.controllers.EmployeeController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.employee.EmployeeViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeControllerImpl implements EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeControllerImpl(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}

	@Override
	@GetMapping("/")
	public String getEmployees(@ModelAttribute EmployeeViewModel viewModel,
						Model model) {
		return "";
	}

	@Override
	@GetMapping("/{id}")
	public String getEmployee
			(@PathVariable Long id,
			 Model model) {
		return "";
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {
		return "";
	}

	@Override
	@PostMapping("/create")
	public String saveEmployee
			(@ModelAttribute EmployeeViewModel viewModel,
			 Model model) {
		return "";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteEmployee
			(@PathVariable Long id) {
		return "";
	}

	@Override
	public String updateEmployee
			(@PathVariable Long id,
			 @ModelAttribute EmployeeViewModel viewModel,
			 Model model) {
		return "";
	}
}
