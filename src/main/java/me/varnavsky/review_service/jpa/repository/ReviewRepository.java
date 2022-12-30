package me.varnavsky.productreviewservice.jpa.repository;

import me.varnavsky.productreviewservice.jpa.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByProductId(String productId);
    Optional<ReviewEntity> getByIdAndProductId(Long id, String productId);
    void deleteByIdAndProductId(Long id, String productId);
}
