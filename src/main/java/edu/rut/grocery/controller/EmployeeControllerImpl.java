package edu.rut.grocery.controller;

import edu.rut.grocery.dto.EmployeeDto;
import edu.rut.grocery.service.EmployeeService;
import edu.rut.web.controllers.EmployeeController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.employee.CreateEmployeeForm;
import edu.rut.web.dto.employee.CreateEmployeeViewModel;
import edu.rut.web.dto.employee.EditEmployeeForm;
import edu.rut.web.dto.employee.EditEmployeeViewModel;
import edu.rut.web.dto.employee.EmployeeListViewModel;
import edu.rut.web.dto.employee.EmployeeSearchForm;
import edu.rut.web.dto.employee.EmployeeViewModel;
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

		Page<EmployeeDto> employees = employeeService.getEmployees(page, size);
		List<EmployeeViewModel> employeeViewModel = employees
				.stream()
				.map(e -> new EmployeeViewModel(
						e.id(),
						e.firstName(),
						e.lastName(),
						e.phone(),
						e.address(),
						null))
				.toList();

		EmployeeListViewModel viewModel = new EmployeeListViewModel(
				createBaseViewModel("Employee list"),
				employeeViewModel,
				page
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

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

		EmployeeDto employeeDto = new EmployeeDto(
				null,
				form.firstName(),
				form.lastName(),
				form.phone(),
				form.storeAddress());

		employeeService.saveEmployee(employeeDto);
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

		EmployeeDto employeeDto = new EmployeeDto(
				id,
				form.firstName(),
				form.lastName(),
				form.phone(),
				form.storeAddress());

		employeeService.updateEmployee(employeeDto, id);

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
