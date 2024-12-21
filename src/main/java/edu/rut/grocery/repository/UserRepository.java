package edu.rut.grocery.repository;

import edu.rut.grocery.domain.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);
}
