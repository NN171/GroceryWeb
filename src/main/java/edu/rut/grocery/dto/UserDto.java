package edu.rut.grocery.dto;

public record UserDto(
		String username,
		String password,
		String passwordConfirm
) {
}
