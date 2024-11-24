package edu.rut.grocery.controller;

import edu.rut.web.controllers.HomeController;
import edu.rut.web.dto.BaseViewModel;
import edu.rut.web.dto.HomeViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class HomeControllerImpl implements HomeController {

	@Override
	@GetMapping("/")
	public String index(Model model) {
		var viewModel = new HomeViewModel(createBaseViewModel("Grocery Web App"));
		model.addAttribute("model", viewModel);
		return "index";
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}
}
