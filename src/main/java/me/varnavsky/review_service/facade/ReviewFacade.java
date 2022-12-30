package me.varnavsky.productreviewservice.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.varnavsky.productreviewservice.jpa.entity.ReviewEntity;
import me.varnavsky.productreviewservice.model.review.ReviewCreateDto;
import me.varnavsky.productreviewservice.model.review.ProductReviewDto;
import me.varnavsky.productreviewservice.model.review.ReviewDto;
import me.varnavsky.productreviewservice.service.ReviewService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewFacade {

  private final ReviewService reviewService;

  public ProductReviewDto getProductReview(String productId) {
    List<ReviewEntity> reviews = reviewService.getProductReviews(productId);
    return ProductReviewDto.builder()
        .productId(productId)
        .reviewCount(reviews.size())
        .reviewAverageScore(
            BigDecimal.valueOf(
                    reviews.stream()
                        .map(ReviewEntity::getScore)
                        .collect(Collectors.averagingDouble(Integer::doubleValue)))
                .setScale(3, RoundingMode.HALF_UP))
        .build();
  }

  public ReviewDto getReview(Long id) {
    ReviewEntity review = reviewService.getReview(id);
    return ReviewDto.builder()
        .id(review.getId())
        .score(review.getScore())
        .build();
  }

  public Long createReview(String productId, ReviewCreateDto productReview) {
    return reviewService.createReview(productId, productReview);
  }

  public void editReview(Long id, String productId, ReviewCreateDto productReview) {
    reviewService.editReview(id, productId, productReview);
  }

  public void deleteReview(Long id, String productId) {
    reviewService.deleteReview(id, productId);
  }
}
