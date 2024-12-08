package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {

	List<Order> getOrdersByCustomerId(Long id);
}
