package me.varnavsky.review_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.varnavsky.review_service.jpa.entity.ReviewEntity;
import me.varnavsky.review_service.jpa.repository.ReviewRepository;
import me.varnavsky.review_service.model.review.ProductReviewDto;
import me.varnavsky.review_service.model.review.ReviewCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

  private final ReviewRepository repository;

  @Transactional
  public ProductReviewDto getReviewCountAndAvg(String productId) {
    return repository
        .getCountAndAverageByProductId(productId)
        .orElse(ProductReviewDto.builder().productId(productId).build());
  }

  @Transactional
  public List<ReviewEntity> list() {
    return repository.findAll();
  }

  @Transactional
  public ReviewEntity getReview(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("review not found by id " + id));
  }

  @Transactional
  public Long createReview(String productId, ReviewCreateDto reviewCreateDto) {
    ReviewEntity entity =
        ReviewEntity.builder().productId(productId).score(reviewCreateDto.getScore()).build();
    return repository.save(entity).getId();
  }

  @Transactional
  public void editReview(Long id, String productId, ReviewCreateDto reviewCreateDto) {
    ReviewEntity entity =
        repository
            .getByIdAndProductId(id, productId)
            .orElseThrow(() -> new RuntimeException("Wrong combination id/productId"));
    entity.setScore(reviewCreateDto.getScore());
    repository.save(entity);
  }

  @Transactional
  public void deleteReview(Long id, String productId) {
    repository.deleteByIdAndProductId(id, productId);
  }
}
