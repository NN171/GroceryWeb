package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {

	boolean deleteById(Long id);
}
