package me.varnavsky.review_service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.varnavsky.review_service.jpa.entity.ReviewEntity;
import me.varnavsky.review_service.model.review.ProductReviewDto;
import me.varnavsky.review_service.model.review.ReviewCreateDto;
import me.varnavsky.review_service.model.review.ReviewDto;
import me.varnavsky.review_service.service.ReviewService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewFacade {

  private final ReviewService reviewService;

  /**
   * Get product review from service and construct DTO from it.
   *
   * @param productId Product id
   * @return ProductReviewDto
   */
  public ProductReviewDto getProductReview(String productId) {
    ProductReviewDto review = reviewService.getReviewCountAndAvg(productId);
    if (review.getReviewAverageScore() != null) {
      review.setReviewAverageScore(
          BigDecimal.valueOf(review.getReviewAverageScore())
              .setScale(2, RoundingMode.HALF_UP)
              .doubleValue());
    }
    return review;
  }

  /**
   * List all reviews
   *
   * @return List of ReviewEntity
   */
  public List<ReviewEntity> list() {
    return reviewService.list();
  }

  /**
   * Get review by id
   *
   * @param id Review id
   * @return ReviewDto
   */
  public ReviewDto getReview(Long id) {
    ReviewEntity review = reviewService.getReview(id);
    return ReviewDto.builder().id(review.getId()).score(review.getScore()).build();
  }

  /**
   * Create new review
   *
   * @param productId Product id
   * @param productReview Review to add to db
   * @return Id of new entity
   */
  public Long createReview(String productId, ReviewCreateDto productReview) {
    return reviewService.createReview(productId, productReview);
  }

  /**
   * Edit review
   *
   * @param id Review id
   * @param productId Product id
   * @param productReview New review
   */
  public void editReview(Long id, String productId, ReviewCreateDto productReview) {
    reviewService.editReview(id, productId, productReview);
  }

  /**
   * Delete review
   *
   * @param id Review id
   * @param productId Product id
   */
  public void deleteReview(Long id, String productId) {
    reviewService.deleteReview(id, productId);
  }
}
