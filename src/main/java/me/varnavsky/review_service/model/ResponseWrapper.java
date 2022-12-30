package me.varnavsky.review_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
