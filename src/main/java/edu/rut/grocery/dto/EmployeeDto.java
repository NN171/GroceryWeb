package edu.rut.grocery.dto;

public record EmployeeDto(
		Long id,
		String firstName,
		String lastName,
		String phone,
		String address
) {
}
