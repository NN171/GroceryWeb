package edu.rut.grocery.service;

import edu.rut.grocery.domain.User;
import edu.rut.grocery.dto.UserDto;

public interface AuthService {

	void register(UserDto userDto);

	User getUser(String username);

	boolean passwordsCheck(UserDto userDto);

	boolean userExists(UserDto userDto);
}
