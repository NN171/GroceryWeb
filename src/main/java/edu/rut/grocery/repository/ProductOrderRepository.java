package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Order;
import edu.rut.grocery.domain.Product;
import edu.rut.grocery.domain.ProductOrder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOrderRepository extends BaseRepository<ProductOrder, Long> {

	Optional<ProductOrder> getByOrderAndProduct(Order order, Product product);

	void delete(ProductOrder productOrder);
}
