package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Store;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends BaseRepository<Store, Long> {

	boolean deleteById(Long id);

	Optional<Store> findByAddress(String address);
}
