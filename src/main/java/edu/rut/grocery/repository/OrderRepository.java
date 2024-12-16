package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Order;
import edu.rut.grocery.domain.Status;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {

	List<Order> getOrdersByCustomerId(Long id);

	Order getByCustomerIdAndStatus(Long customerId, Status status);
}
