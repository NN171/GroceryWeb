package edu.rut.grocery.repository;

import edu.rut.grocery.model.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {
}
