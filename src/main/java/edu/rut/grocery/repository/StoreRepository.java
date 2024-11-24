package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Store;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends BaseRepository<Store, Long> {

	boolean deleteById(Long id);
}
