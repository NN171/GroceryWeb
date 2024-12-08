package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Employee;
import edu.rut.grocery.domain.Order;
import edu.rut.grocery.domain.Store;
import edu.rut.grocery.dto.StoreDto;
import edu.rut.grocery.repository.StoreRepository;
import edu.rut.grocery.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

	private final ModelMapper modelMapper;
	private final StoreRepository storeRepository;

	public StoreServiceImpl(StoreRepository storeRepository, ModelMapper modelMapper) {
		this.storeRepository = storeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Page<StoreDto> getStores(int page, int size) {

		Pageable pageable = PageRequest.of(page-1, size, Sort.by("address").ascending());
		Page<Store> stores = storeRepository.findAll(pageable);


		return new PageImpl<>(
				stores.getContent().stream()
						.map(element -> modelMapper.map(element, StoreDto.class))
						.collect(Collectors.toList()
						),
				stores.getPageable(),
				stores.getTotalPages()
		);
	}

	@Override
	public StoreDto getStore(Long id) {
		Store store = storeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));

		return modelMapper.map(store, StoreDto.class);
	}

	@Override
	public String saveStore(StoreDto storeDto) {
		Store store = modelMapper.map(storeDto, Store.class);
		storeRepository.save(store);

		return "Store saved";
	}

	@Override
	public String deleteStore(Long id) {
		boolean removed = storeRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Store not found");

		return "Success";
	}

	@Override
	public int getEmployeesCount(Long id) {

		Store store = storeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));

		Set<Employee> employees = store.getEmployees();

		return employees.size();
	}

	@Override
	public double countSold(Long id) {

		Store store = storeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));

		Set<Employee> employees = store.getEmployees();
		double sells = 0;

		for (Employee employee : employees) {

			Set<Order> orders = employee.getOrders();

			for (Order order : orders) {
				sells += order.getPrice();
			}
		}

		return sells;
	}

	@Override
	public String updateStore(StoreDto StoreDto, Long id) {
		Store store = storeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));
		modelMapper.map(StoreDto, store);
		storeRepository.save(store);
		return "Store updated";
	}
}
