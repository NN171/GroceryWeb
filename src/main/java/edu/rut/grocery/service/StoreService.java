package edu.rut.grocery.service;

import edu.rut.grocery.dto.StoreDto;
import org.springframework.data.domain.Page;

public interface StoreService {

	Page<StoreDto> getStores(int page, int size);

	StoreDto getStore(Long id);

	String saveStore(StoreDto storeDto);

	String deleteStore(Long id);

	String updateStore(StoreDto StoreDto, Long id);

	int getEmployeesCount(Long id);

	double countSold(Long id);
}
