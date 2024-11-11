package br.com.samueltorga.spring.controller.exceptionhandler;

import br.com.samueltorga.spring.controller.dto.ErrorResponse;
import br.com.samueltorga.spring.exceptions.MaxPageSizeException;
import br.com.samueltorga.spring.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException e) {
        return ResponseEntity
                .internalServerError()
                .body(
                        ErrorResponse.builder()
                                .title("Resource not found")
                                .detail(e.getMessage())
                                .status(HttpStatus.NOT_FOUND)
                                .timestamp(OffsetDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(MaxPageSizeException.class)
    public ResponseEntity<ErrorResponse> handleException(MaxPageSizeException e) {
        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponse.builder()
                                .title(e.getMessage())
                                .detail(e.getMessage())
                                .status(HttpStatus.BAD_REQUEST)
                                .timestamp(OffsetDateTime.now())
                                .build()
                );
    }

}
