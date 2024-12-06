package edu.rut.grocery.service;

import edu.rut.grocery.dto.StoreDto;

import java.util.List;

public interface StoreService {

	List<StoreDto> getStores(int page, int size);

	StoreDto getStore(Long id);

	String saveStore(StoreDto storeDto);

	String deleteStore(Long id);

	String updateStore(StoreDto StoreDto, Long id);
}
