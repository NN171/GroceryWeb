package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Customer;
import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends BaseRepository<Feedback, Long> {

	Customer getCustomerById(Long id);

	Product getProductById(Long id);

	List<Feedback> findAll();

	boolean deleteById(Long id);
}
