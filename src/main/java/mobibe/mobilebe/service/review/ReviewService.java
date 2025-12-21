package mobibe.mobilebe.service.review;


import java.util.List;

import mobibe.mobilebe.dto.request.review.AddReviewReq;
import mobibe.mobilebe.dto.request.review.EditReviewReq;
import mobibe.mobilebe.dto.response.review.ReviewRes;
import mobibe.mobilebe.dto.response.review.ReviewSummaryRes;

public interface ReviewService {

    int add(AddReviewReq req);

    void edit(int reviewId, EditReviewReq req);

    void delete(int reviewId, int userId);

    List<ReviewRes> getByProduct(int productId);

    ReviewSummaryRes getSummary(int productId);
}
