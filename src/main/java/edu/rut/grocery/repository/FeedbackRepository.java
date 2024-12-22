package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends BaseRepository<Feedback, Long> {

	List<Feedback> findAll();

	boolean deleteById(Long id);

	Page<Feedback> getFeedbacksByProductId(Long productId, Pageable pageable);
}
