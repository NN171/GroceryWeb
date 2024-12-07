package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.dto.CustomerDto;
import edu.rut.grocery.dto.EmployeeDto;
import edu.rut.grocery.service.EmployeeService;
import edu.rut.web.controllers.EmployeeController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.customer.CreateCustomerForm;
import edu.rut.web.dto.customer.CreateCustomerViewModel;
import edu.rut.web.dto.customer.EditCustomerForm;
import edu.rut.web.dto.customer.EditCustomerViewModel;
import edu.rut.web.dto.employee.CreateEmployeeForm;
import edu.rut.web.dto.employee.CreateEmployeeViewModel;
import edu.rut.web.dto.employee.EditEmployeeForm;
import edu.rut.web.dto.employee.EditEmployeeViewModel;
import edu.rut.web.dto.employee.EmployeeSearchForm;
import edu.rut.web.dto.employee.EmployeeViewModel;
import jakarta.validation.Valid;
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
@RequestMapping("/employees")
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
	public String getEmployees(@ModelAttribute("form") EmployeeSearchForm form,
						Model model) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		List<EmployeeDto> customers = employeeService.getEmployees(page, size);
		model.addAttribute("customers", customers);

		return "employee/employee-list";
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {

		CreateEmployeeViewModel viewModel = new CreateEmployeeViewModel(
				createBaseViewModel("Create employee")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new CreateEmployeeForm(
				"", "", "", ""));

		return "employee/employee-create";
	}

	@Override
	@PostMapping("/create")
	public String saveEmployee
			(@Valid @ModelAttribute("form") CreateEmployeeForm form,
			 BindingResult bindingResult,
			 Model model) {

		if (bindingResult.hasErrors()) {
			CreateEmployeeViewModel viewModel = new CreateEmployeeViewModel(
					createBaseViewModel("Create employee")
			);
			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "employee/employee-create";
		}

		return "redirect:/employees";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable Long id) {

		employeeService.deleteEmployee(id);
		return "redirect:/employees";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateEmployee
			(@PathVariable Long id,
			 @Valid @ModelAttribute("form") EditEmployeeForm form,
			 BindingResult bindingResult,
			 Model model) {

		if (bindingResult.hasErrors()) {
			EditEmployeeViewModel viewModel = new EditEmployeeViewModel(
					createBaseViewModel("Edit employee")
			);
			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "employee/employee-edit";
		}

		return "redirect:/employees";
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		EmployeeDto employee = employeeService.getEmployee(id);
		EditEmployeeViewModel viewModel = new EditEmployeeViewModel(
				createBaseViewModel("Edit employee"));

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditEmployeeForm(
				employee.id(),
				employee.firstName(),
				employee.lastName(),
				employee.phone(),
				employee.address()
		));

		return "employee/employee-edit";
	}
}
