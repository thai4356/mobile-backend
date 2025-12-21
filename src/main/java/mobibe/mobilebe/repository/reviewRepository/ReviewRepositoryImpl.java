package mobibe.mobilebe.repository.reviewRepository;

import java.util.List;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQuery;

import mobibe.mobilebe.entity.review.QReview;
import mobibe.mobilebe.entity.review.Review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final EntityManager entityManager;

    private final QReview review = QReview.review;

    // ====== EXISTS ======
    @Override
    public boolean existsByProductAndUser(int productId, int userId) {

        Integer result = new JPAQuery<Integer>(entityManager)
                .select(review.id) // ❌ không selectOne
                .from(review)
                .where(
                        review.product.id.eq(productId),
                        review.user.id.eq(userId))
                .fetchFirst();

        return result != null;
    }

    // ====== AVG RATING ======
    @Override
    public int getAverageRating(int productId) {

        Double avg = new JPAQuery<Double>(entityManager)
                .select(review.rating.avg())
                .from(review)
                .where(review.product.id.eq(productId))
                .fetchOne();

        return avg == null ? 0 : avg.intValue();
    }

    // ====== COUNT ======
    @Override
    public int countByProduct(int productId) {

        Long count = new JPAQuery<Long>(entityManager)
                .select(review.count())
                .from(review)
                .where(review.product.id.eq(productId))
                .fetchOne();

        return count == null ? 0 : count.intValue();
    }

    // ====== LIST ======
    @Override
    public List<Review> findByProduct(int productId) {

        return new JPAQuery<Review>(entityManager)
                .select(review)
                .from(review)
                .where(review.product.id.eq(productId))
                .orderBy(review.createdAt.desc())
                .fetch();
    }
}
