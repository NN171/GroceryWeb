package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Feedback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends BaseRepository<Feedback, Long> {

	List<Feedback> getFeedbacksByCustomerId(Long id);

	boolean deleteById(Long id);
}
