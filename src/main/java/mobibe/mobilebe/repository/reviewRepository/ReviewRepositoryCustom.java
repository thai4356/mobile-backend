package mobibe.mobilebe.repository.reviewRepository;

import java.util.List;
import mobibe.mobilebe.entity.review.Review;

public interface ReviewRepositoryCustom {
    List<Review> findByProduct(int productId);

    boolean existsByProductAndUser(int productId, int userId);

    int getAverageRating(int productId);

    int countByProduct(int productId);
}
