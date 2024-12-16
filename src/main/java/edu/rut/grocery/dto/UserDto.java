package edu.rut.grocery.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class UserDto implements Serializable {
	@NotBlank(message = "Username cannot be empty") String username;
	@NotBlank(message = "Password cannot be empty") String password;
	@NotBlank(message = "Password confirmation cannot be empty") String passwordConfirm;

	public UserDto() {
	}

	public UserDto(String username, String password, String passwordConfirm) {
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public @NotBlank(message = "Username cannot be empty") String getUsername() {
		return username;
	}

	public void setUsername(@NotBlank(message = "Username cannot be empty") String username) {
		this.username = username;
	}

	public @NotBlank(message = "Password cannot be empty") String getPassword() {
		return password;
	}

	public void setPassword(@NotBlank(message = "Password cannot be empty") String password) {
		this.password = password;
	}

	public @NotBlank(message = "Password confirmation cannot be empty") String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(@NotBlank(message = "Password confirmation cannot be empty") String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
