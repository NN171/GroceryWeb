package edu.rut.grocery.repository;

import edu.rut.grocery.model.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

	boolean deleteById(Long id);
}
