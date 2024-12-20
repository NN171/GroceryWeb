package edu.rut.grocery.config;

import edu.rut.grocery.domain.Employee;
import edu.rut.grocery.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapperBean() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.typeMap(Employee.class, EmployeeDto.class)
				.addMapping(src -> src.getStore().getAddress(), EmployeeDto::setAddress);

		return modelMapper;
	}
}
