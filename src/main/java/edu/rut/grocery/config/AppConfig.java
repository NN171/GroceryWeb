package edu.rut.grocery.config;

import edu.rut.grocery.domain.Employee;
import edu.rut.grocery.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapperBean() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.addMappings(new PropertyMap<Employee, EmployeeDto>() {
			@Override
			protected void configure() {
				map().setAddress(source.getStore().getAddress());
			}
		});
		return new ModelMapper();
	}
}
