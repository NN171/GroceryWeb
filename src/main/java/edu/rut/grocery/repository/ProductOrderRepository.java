package edu.rut.grocery.repository;

import edu.rut.grocery.domain.ProductOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends BaseRepository<ProductOrder, Long> {
}
