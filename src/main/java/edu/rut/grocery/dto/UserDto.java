package edu.rut.grocery.dto;

import java.io.Serializable;

public class UserDto implements Serializable {
	private String username;
	private String password;
	private String passwordConfirm;
	private String firstName;
	private String lastName;
	private String phone;

	public UserDto() {
	}

	public UserDto(String username, String password, String passwordConfirm, String firstName, String lastName, String phone) {
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
