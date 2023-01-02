package me.varnavsky.review_service.jpa.repository;

import me.varnavsky.review_service.jpa.entity.ReviewEntity;
import me.varnavsky.review_service.model.review.ProductReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

  @Query(
      "select new me.varnavsky.review_service.model.review.ProductReviewDto(r.productId, avg(r.score), count(r.id)) "
          + "from ReviewEntity r where r.productId = :productId group by r.productId")
  Optional<ProductReviewDto> getCountAndAverageByProductId(String productId);

  Optional<ReviewEntity> getByIdAndProductId(Long id, String productId);

  void deleteByIdAndProductId(Long id, String productId);
}
