package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Store;
import edu.rut.grocery.model.dto.StoreDtoe;
import edu.rut.grocery.repository.StoreRepository;
import edu.rut.grocery.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

	private final ModelMapper modelMapper;
	private final StoreRepository storeRepository;

	public StoreServiceImpl(StoreRepository storeRepository, ModelMapper modelMapper) {
		this.storeRepository = storeRepository;
		this.modelMapper = modelMapper;
	}

	public List<StoreDto> getStores() {
		List<Store> stores = storeRepository.findAll()
				.orElseThrow(() -> new EntityNotFoundException("Stores not found"));

		return stores.stream()
				.map(store -> modelMapper.map(store, StoreDto.class))
				.collect(Collectors.toList());
	}

	public StoreDto getStore(Long id) {
		Store store = storeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));

		return modelMapper.map(store, StoreDto.class);
	}

	public String saveStore(StoreDto storeDto) {
		Store store = modelMapper.map(storeDto, Store.class);
		storeRepository.save(store);

		return "Store saved";
	}

	public String deleteStore(Long id) {
		boolean removed = storeRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Store not found");

		return "Success";
	}

	public String updateStore(StoreDto StoreDto, Long id) {
		Store store = storeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));
		modelMapper.map(StoreDto, store);
		storeRepository.save(store);
		return "Store updated";
	}
}
