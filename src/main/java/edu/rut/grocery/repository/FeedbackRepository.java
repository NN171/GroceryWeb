package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Feedback;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends BaseRepository<Feedback, Long> {

	boolean deleteById(Long id);
}
