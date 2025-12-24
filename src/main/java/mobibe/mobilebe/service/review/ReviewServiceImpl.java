package mobibe.mobilebe.service.review;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import mobibe.mobilebe.dto.request.review.AddReviewReq;
import mobibe.mobilebe.dto.request.review.EditReviewReq;
import mobibe.mobilebe.dto.response.review.ReviewRes;
import mobibe.mobilebe.dto.response.review.ReviewSummaryRes;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.review.Review;
import mobibe.mobilebe.entity.user.User;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.productRepository.ProductRepository;
import mobibe.mobilebe.repository.reviewRepository.ReviewRepository;
import mobibe.mobilebe.repository.userRepository.UserRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository,
            UserRepository userRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    // ========== ADD ==========
    @Override
    @Transactional
    public int add(AddReviewReq req) {

        if (reviewRepository.existsByProductAndUser( 
                req.getProductId(), req.getUserId())) {
            throw new BusinessException("User already reviewed this product");
        }

        Product product = productRepository.findById(req.getProductId());

        if (product == null) {
            throw new BusinessException("product_not_found");
        }

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new BusinessException("User not found"));

        Review review = new Review();
        review.setRating(req.getRating());
        review.setComment(req.getComment());
        review.setProduct(product);
        review.setUser(user);

        reviewRepository.save(review);
        return review.getId();
    }

    // ========== EDIT ==========
    @Override
    @Transactional
    public void edit(int reviewId, EditReviewReq req) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException("Review not found"));

        if (!(review.getUser().getId() == req.getUserId())) {
            throw new BusinessException("No permission to edit review");
        }

        if (req.getRating() != null) {
        review.setRating(req.getRating());
        }
        if (req.getComment() != null) {
            review.setComment(req.getComment());
        }

        reviewRepository.save(review);
    }

    // ========== DELETE ==========
    @Override
    @Transactional
    public void delete(int reviewId, int userId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException("Review not found"));

        if (!(review.getUser().getId() == userId)) {
            throw new BusinessException("No permission to delete review");
        }

        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewRes> getByProduct(int productId) {

        return reviewRepository.findByProduct(productId)
                .stream()
                .map(r -> {
                    ReviewRes res = new ReviewRes();
                    res.setId(r.getId());
                    res.setRating(r.getRating());
                    res.setComment(r.getComment());
                    res.setUserId(r.getUser().getId());
                    res.setProductId(r.getProduct().getId());
                    return res;
                })
                .toList();
    }

    @Override
    public ReviewSummaryRes getSummary(int productId) {

        ReviewSummaryRes res = new ReviewSummaryRes();
        res.setAverageRating(
                reviewRepository.getAverageRating(productId));
        res.setTotalReview(
                reviewRepository.countByProduct(productId));
        return res;
    }

}
