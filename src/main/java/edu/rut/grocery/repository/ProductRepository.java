package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

	List<Product> getAll();

	boolean deleteById(Long id);
}
