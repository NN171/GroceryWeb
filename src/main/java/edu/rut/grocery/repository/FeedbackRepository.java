package edu.rut.grocery.repository;

import edu.rut.grocery.model.domain.Feedback;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends BaseRepository<Feedback, Long> {
}