package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Role;
import edu.rut.grocery.domain.User;
import edu.rut.grocery.dto.UserDto;
import edu.rut.grocery.repository.UserRepository;
import edu.rut.grocery.service.AuthService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void register(UserDto userDto) {

		if (!userDto.passwordConfirm().equals(userDto.password())) {
			throw new RuntimeException("Password not matches");
		}

		Optional<User> existingUser = userRepository.findByUsername(userDto.username());

		if (existingUser.isPresent()) {
			throw new RuntimeException("Username is used");
		}

		User user = new User(
				userDto.username(),
				passwordEncoder.encode(userDto.password()),
				Role.USER
		);

		userRepository.save(user);
	}

	public User getUser(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}
}
