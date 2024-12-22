package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Customer;
import edu.rut.grocery.domain.Role;
import edu.rut.grocery.domain.User;
import edu.rut.grocery.dto.UserDto;
import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.repository.UserRepository;
import edu.rut.grocery.service.AuthService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository customerRepository;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
	}

	@Override
	@CacheEvict(value = "register", allEntries = true)
	public void register(UserDto userDto) {

		Customer customer = new Customer(
				userDto.getFirstName(),
				userDto.getLastName(),
				userDto.getPhone(),
				0L,
				0,
				new HashSet<>(),
				new HashSet<>(),
				null
		);

		User user = new User(
				userDto.getUsername(),
				passwordEncoder.encode(userDto.getPassword()),
				Role.USER,
				customer
		);

		customer.setUser(user);

		userRepository.save(user);
	}

	@Override
	public boolean passwordsCheck(UserDto userDto) {

		return userDto.getPasswordConfirm().equals(userDto.getPassword());
	}

	@Override
	public boolean userExists(UserDto userDto) {

		return userRepository.existsByUsername(userDto.getUsername());
	}

	@Override
	@Cacheable(value = "getUser", key = "#username")
	public User getUser(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}
}
