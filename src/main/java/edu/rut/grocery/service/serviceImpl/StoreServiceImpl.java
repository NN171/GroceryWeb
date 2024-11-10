package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.repository.StoreRepository;
import edu.rut.grocery.service.StoreService;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

	public StoreServiceImpl(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}
}
