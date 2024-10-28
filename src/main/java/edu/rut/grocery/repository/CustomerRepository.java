package edu.rut.grocery.repository;

import edu.rut.grocery.model.domain.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {
}
