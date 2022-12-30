package me.varnavsky.review_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.varnavsky.review_service.facade.ReviewFacade;
import me.varnavsky.review_service.model.ResponseWrapper;
import me.varnavsky.review_service.model.review.ReviewCreateDto;
import me.varnavsky.review_service.model.review.ProductReviewDto;
import me.varnavsky.review_service.model.review.ReviewDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = "/review", produces = APPLICATION_JSON_VALUE + ";charset=utf-8")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

  private final ReviewFacade facade;

  /**
   * Get product review. Includes score average value and count.
   *
   * @param productId Product id
   * @return {{@link ProductReviewDto}}
   */
  @GetMapping("/product/{productId}")
  @Operation(summary = "Get product review data: score average value and count")
  public ResponseWrapper<ProductReviewDto> getProductReview(@PathVariable String productId) {
    return ResponseWrapper.of(facade.getProductReview(productId));
  }

  /**
   * Get product review. Includes score average value and count.
   *
   * @param id Review id
   * @return {{@link ProductReviewDto}}
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get review data")
  public ResponseWrapper<ReviewDto> getReview(@PathVariable Long id) {
    return ResponseWrapper.of(facade.getReview(id));
  }

  /**
   * Create product review.
   *
   * @param productId Product id
   * @return Id for created entity
   */
  @PostMapping("/product/{productId}")
  @Operation(summary = "Create new product review")
  public ResponseWrapper<Long> createProductReview(
      @PathVariable String productId, @RequestBody ReviewCreateDto reviewCreateDto) {
    return ResponseWrapper.of(facade.createReview(productId, reviewCreateDto));
  }

  /**
   * Update product review. Need to know review id!
   *
   * <p>Status = 200 means success.
   *
   * @param productId Product id
   * @param reviewId Review id
   * @return void
   */
  @PutMapping("/{reviewId}/{productId}")
  @Operation(summary = "Update product review")
  public ResponseWrapper<Void> editProductReview(
      @PathVariable String productId,
      @PathVariable Long reviewId,
      @RequestBody ReviewCreateDto reviewCreateDto) {
    facade.editReview(reviewId, productId, reviewCreateDto);
    return ResponseWrapper.of(null);
  }

  /**
   * Delete product review. Need to know review id!
   *
   * <p>Status = 200 means success.
   *
   * @param productId Product id
   * @param reviewId Review id
   * @return void
   */
  @DeleteMapping("/{reviewId}/{productId}")
  @Operation(summary = "Delete product review")
  public ResponseWrapper<Void> deleteProduct(
      @PathVariable String productId, @PathVariable Long reviewId) {
    facade.deleteReview(reviewId, productId);
    return ResponseWrapper.of(null);
  }
}
