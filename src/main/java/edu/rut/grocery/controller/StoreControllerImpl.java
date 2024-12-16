package edu.rut.grocery.controller;

import edu.rut.grocery.dto.StoreDto;
import edu.rut.grocery.service.StoreService;
import edu.rut.web.controllers.StoreController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.store.CreateStoreForm;
import edu.rut.web.dto.store.CreateStoreViewModel;
import edu.rut.web.dto.store.EditStoreForm;
import edu.rut.web.dto.store.EditStoreViewModel;
import edu.rut.web.dto.store.StoreListViewModel;
import edu.rut.web.dto.store.StoreSearchForm;
import edu.rut.web.dto.store.StoreViewModel;
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
	@GetMapping("/")
	public String getStores(@ModelAttribute("form") StoreSearchForm form,
							Model model) {

		int page = form.page() != null ? form.page() : 1;
		int size = form.size() != null ? form.size() : 5;

		Page<StoreDto> stores = storeService.getStores(page, size);
		List<StoreViewModel> StoreViewModel = stores
				.stream()
				.map(s -> new StoreViewModel(
						s.getId(),
						s.getAddress(),
						s.getEmployeesNum(),
						s.getSoldAmount(),
						null))
				.toList();

		StoreListViewModel viewModel = new StoreListViewModel(
				createBaseViewModel("Store list"),
				StoreViewModel,
				page,
				stores.getTotalPages()
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", form);

		return "store/store-list";
	}

	@Override
	@GetMapping("/create")
	public String createForm(Model model) {

		CreateStoreViewModel viewModel = new CreateStoreViewModel(
				createBaseViewModel("Create store")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new CreateStoreForm(
				""
		));

		return "redirect:/stores";
	}

	@Override
	@PostMapping("/create")
	public String saveStore(@Valid @ModelAttribute("form") CreateStoreForm form,
							BindingResult bindingResult,
							Model model) {

		if (bindingResult.hasErrors()) {

			CreateStoreViewModel viewModel = new CreateStoreViewModel(
					createBaseViewModel("Create store")
			);

			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);


			return "store/store-create";
		}

		StoreDto storeDto = new StoreDto(
				null,
				form.address(),
				null,
				null
		);

		storeService.saveStore(storeDto);

		return "redirect:/stores";
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public String deleteStore(@PathVariable Long id) {

		storeService.deleteStore(id);

		return "redirect:/stores";
	}

	@Override
	@PutMapping("/update/{id}")
	public String updateStore(@PathVariable Long id,
							  @Valid @ModelAttribute("form") EditStoreForm form,
							  BindingResult bindingResult,
							  Model model) {

		if (bindingResult.hasErrors()) {

			EditStoreViewModel viewModel = new EditStoreViewModel(
					createBaseViewModel("Edit store")
			);

			model.addAttribute("model", viewModel);
			model.addAttribute("form", form);

			return "store/store-edit";
		}

		StoreDto storeDto = new StoreDto(
				id,
				form.address(),
				storeService.getEmployeesCount(id),
				storeService.countSold(id)
		);

		storeService.updateStore(storeDto, id);

		return "redirect:/stores";
	}

	@Override
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {

		StoreDto storeDto = storeService.getStore(id);

		EditStoreViewModel viewModel = new EditStoreViewModel(
				createBaseViewModel("Edit store")
		);

		model.addAttribute("model", viewModel);
		model.addAttribute("form", new EditStoreForm(
				storeDto.getId(),
				storeDto.getAddress()
		));

		return "store/store-edit";
	}
}
