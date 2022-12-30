package me.varnavsky.productreviewservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> {

    ZonedDateTime datetime;
    String errorMessage;
    T payload;

    public static <T> ResponseWrapper<T> of(T payload) {
        return new ResponseWrapper<>(ZonedDateTime.now(), null, payload);
    }

    public static <T> ResponseWrapper<T> failureOf(Exception e) {
        return new ResponseWrapper<>(ZonedDateTime.now(), e.getMessage(), null);
    }
}
