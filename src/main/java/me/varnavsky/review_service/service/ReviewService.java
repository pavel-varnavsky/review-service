package me.varnavsky.productreviewservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.varnavsky.productreviewservice.jpa.entity.ReviewEntity;
import me.varnavsky.productreviewservice.jpa.repository.ReviewRepository;
import me.varnavsky.productreviewservice.model.review.ReviewCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

  private final ReviewRepository repository;

  @Transactional
  public List<ReviewEntity> getProductReviews(String productId) {
    return repository.findAllByProductId(productId);
  }

  @Transactional
  public ReviewEntity getReview(Long id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("review not found by id " + id));
  }

  @Transactional
  public Long createReview(String productId, ReviewCreateDto reviewCreateDto) {
    ReviewEntity entity =
        ReviewEntity.builder()
            .productId(productId)
            .score(reviewCreateDto.getScore())
            .build();
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
