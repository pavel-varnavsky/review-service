package me.varnavsky.review_service.model.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductReviewDto {
  String productId;
  Double reviewAverageScore;
  Long reviewCount;
}
