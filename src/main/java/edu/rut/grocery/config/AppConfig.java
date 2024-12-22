package edu.rut.grocery.config;

import edu.rut.grocery.domain.Employee;
import edu.rut.grocery.domain.Order;
import edu.rut.grocery.dto.EmployeeDto;
import edu.rut.grocery.dto.OrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapperBean() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.typeMap(Employee.class, EmployeeDto.class)
				.addMapping(src -> src.getStore().getAddress(), EmployeeDto::setAddress);

		modelMapper.typeMap(Order.class, OrderDto.class)
				.addMapping(Order::getProductOrders, OrderDto::setProducts);

		return modelMapper;
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
