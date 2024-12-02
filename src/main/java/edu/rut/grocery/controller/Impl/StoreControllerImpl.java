package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.StoreService;
import edu.rut.web.controllers.StoreController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.store.StoreViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stores")
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

	public String getStores(StoreViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String getStore(Long id, Model model) {
		return "";
	}

	@Override
	public String createForm(Model model) {
		return "";
	}

	@Override
	public String saveStore(StoreViewModel viewModel, Model model) {
		return "";
	}

	@Override
	public String deleteStore(Long id) {
		return "";
	}

	@Override
	public String updateStore(Long id, StoreViewModel viewModel, Model model) {
		return "";
	}
}
