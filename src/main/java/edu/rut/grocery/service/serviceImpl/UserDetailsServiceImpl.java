package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.map(u -> new User(
						u.getUsername(),
						u.getPassword(),
						List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))
				))
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}
}
