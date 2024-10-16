package br.com.samueltorga.spring.controller.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Builder
public record ErrorResponse(String title, String detail, HttpStatus status, OffsetDateTime timestamp) {

    public ErrorResponse {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (detail == null || detail.isBlank()) {
            throw new IllegalArgumentException("Detail cannot be null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }

}
