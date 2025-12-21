package mobibe.mobilebe.repository.reviewRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import mobibe.mobilebe.entity.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryCustom {
}
