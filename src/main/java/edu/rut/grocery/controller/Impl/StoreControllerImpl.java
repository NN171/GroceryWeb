package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.StoreService;
import edu.rut.web.controllers.StoreController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.store.CreateStoreForm;
import edu.rut.web.dto.store.EditStoreForm;
import edu.rut.web.dto.store.StoreSearchForm;
import edu.rut.web.dto.store.StoreViewModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stores")
public class StoreControllerImpl implements StoreController {

	private final StoreService storeService;

	public StoreControllerImpl(StoreService storeService) {
		this.storeService = storeService;
	}

	@Override
	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}

	@Override

	public String getStores(@ModelAttribute("form") StoreSearchForm form,
							Model model) {
		return "";
	}

	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	public String saveStore(@Valid @ModelAttribute("form") CreateStoreForm form,
							BindingResult bindingResult,
							Model model) {
		return "";
	}

	@Override
	public String deleteStore(@PathVariable Long id) {
		return "";
	}

	@Override
	public String updateStore(@PathVariable Long id,
							  @Valid @ModelAttribute("form") EditStoreForm form,
							  BindingResult bindingResult,
							  Model model) {
		return "";
	}
}
