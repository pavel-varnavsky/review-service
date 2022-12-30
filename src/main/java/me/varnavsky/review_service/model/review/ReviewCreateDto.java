package me.varnavsky.productreviewservice.model.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Assume that we have only integer scores */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewCreateDto {

  private Integer score;
}
