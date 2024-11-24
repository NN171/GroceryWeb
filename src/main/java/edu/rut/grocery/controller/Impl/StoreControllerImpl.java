package edu.rut.grocery.controller.Impl;

import edu.rut.grocery.service.StoreService;
import edu.rut.web.controllers.model.StoreController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stores")
public class StoreControllerImpl implements StoreController {

	private final StoreService storeService;

	public StoreControllerImpl(StoreService storeService) {
		this.storeService = storeService;
	}
}
