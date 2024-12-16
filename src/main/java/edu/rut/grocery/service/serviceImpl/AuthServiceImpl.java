package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Role;
import edu.rut.grocery.domain.User;
import edu.rut.grocery.dto.UserDto;
import edu.rut.grocery.repository.UserRepository;
import edu.rut.grocery.service.AuthService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void register(UserDto userDto) {

		User user = new User(
				userDto.getUsername(),
				passwordEncoder.encode(userDto.getPassword()),
				Role.USER
		);

		userRepository.save(user);
	}

	@Override
	public boolean passwordsCheck(UserDto userDto) {

		return userDto.getPasswordConfirm().equals(userDto.getPassword());
	}

	@Override
	public boolean userExists(UserDto userDto) {

		Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());

		return existingUser.isPresent();
	}

	@Override
	public User getUser(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}
}
