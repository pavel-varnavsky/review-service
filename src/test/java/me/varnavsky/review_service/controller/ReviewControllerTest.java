package me.varnavsky.review_service.controller;

import lombok.SneakyThrows;
import me.varnavsky.review_service.config.OtherConfig;
import me.varnavsky.review_service.config.SecurityConfiguration;
import me.varnavsky.review_service.config.UserSecurityProperties;
import me.varnavsky.review_service.facade.ReviewFacade;
import me.varnavsky.review_service.model.review.ProductReviewDto;
import me.varnavsky.review_service.model.review.ReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
@Import(ReviewController.class)
@ContextConfiguration(
    classes = {SecurityConfiguration.class, UserSecurityProperties.class, OtherConfig.class})
class ReviewControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private ReviewFacade reviewFacade;

  @Test
  @SneakyThrows
  void getProductReview() {
    String productId = "BB5476";
    when(reviewFacade.getProductReview(eq(productId))).thenReturn(getProductReviewDto(productId));
    mockMvc
        .perform(
            get("/review/product/" + productId)
                .accept(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .contentType(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=utf-8"))
        .andExpect(jsonPath("$.payload").exists())
        .andExpect(jsonPath("$.payload.productId").value("BB5476"));
  }

  @Test
  @SneakyThrows
  void getReview() {
    Long reviewId = 999L;
    when(reviewFacade.getReview(eq(reviewId))).thenReturn(getReviewDto(reviewId));
    mockMvc
        .perform(
            get("/review/" + reviewId)
                .accept(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .contentType(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=utf-8"))
        .andExpect(jsonPath("$.payload").exists())
        .andExpect(jsonPath("$.payload.id").value(reviewId))
        .andExpect(jsonPath("$.payload.score").exists());
  }

  @Test
  @SneakyThrows
  void createProductReview() {
    Long newReviewId = 999L;
    when(reviewFacade.createReview(eq("BB5476"), any())).thenReturn(newReviewId);
    mockMvc
        .perform(
            post("/review/product/BB5476")
                .content("{\"score\": 1}")
                .accept(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .contentType(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=utf-8"))
        .andExpect(jsonPath("$.payload").exists())
        .andExpect(jsonPath("$.payload").value(newReviewId));
  }

  @Test
  @SneakyThrows
  void editProductReview() {
    String productId = "BB5476";
    Long reviewId = 999L;
    doNothing().when(reviewFacade).editReview(eq(reviewId), eq(productId), any());
    mockMvc
        .perform(
            put("/review/" + reviewId + "/" + productId)
                .content("{\"score\": 1}")
                .accept(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .contentType(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void deleteProduct() {
    String productId = "BB5476";
    Long reviewId = 999L;
    doNothing().when(reviewFacade).deleteReview(eq(reviewId), eq(productId));
    mockMvc
        .perform(
            delete("/review/" + reviewId + "/" + productId)
                .accept(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .contentType(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
        .andDo(print())
        .andExpect(status().isOk());
  }

  private ProductReviewDto getProductReviewDto(String productId) {
    return ProductReviewDto.builder()
        .productId(productId)
        .reviewCount(5L)
        .reviewAverageScore(2.73)
        .build();
  }

  private ReviewDto getReviewDto(Long reviewId) {
    return ReviewDto.builder().id(reviewId).score(new Random().nextInt(5) + 1).build();
  }
}
