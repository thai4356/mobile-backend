package mobibe.mobilebe.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import mobibe.mobilebe.dto.request.review.AddReviewReq;
import mobibe.mobilebe.dto.request.review.EditReviewReq;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.review.ReviewRes;
import mobibe.mobilebe.dto.response.review.ReviewSummaryRes;
import mobibe.mobilebe.service.review.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    
    @PostMapping
    public ResponseEntity<BaseResponse<Integer>> add(
            @RequestBody @Valid AddReviewReq request
    ) {
        int reviewId = reviewService.add(request);
        return ResponseEntity.ok(new BaseResponse<>(reviewId));
    }

    
    @PutMapping("/{reviewId}")
    public ResponseEntity<BaseResponse<Void>> edit(
            @PathVariable int reviewId,
            @RequestBody @Valid EditReviewReq request
    ) {
        reviewService.edit(reviewId, request);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @PathVariable int reviewId,
            @RequestParam int userId
    ) {
        reviewService.delete(reviewId, userId);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    
    @GetMapping("/product/{productId}")
    public ResponseEntity<BaseResponse<List<ReviewRes>>> getByProduct(
            @PathVariable int productId
    ) {
        return ResponseEntity.ok(
                new BaseResponse<>(reviewService.getByProduct(productId))
        );
    }

    
    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<BaseResponse<ReviewSummaryRes>> summary(
            @PathVariable int productId
    ) {
        return ResponseEntity.ok(
                new BaseResponse<>(reviewService.getSummary(productId))
        );
    }
}
