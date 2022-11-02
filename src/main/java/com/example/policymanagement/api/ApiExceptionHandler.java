package com.example.policymanagement.api;

import com.example.policymanagement.exception.InvalidDateException;
import com.example.policymanagement.model.ApiExceptionModel;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

  @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<ApiExceptionModel> handleValidationException(Exception ex) {
    return getBadRequestMessage(ex.getMessage());
  }

  @ExceptionHandler(InvalidDateException.class)
  public ResponseEntity<ApiExceptionModel> handleDateValidationException(InvalidDateException ex) {
    return getBadRequestMessage(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiExceptionModel> handleAnyException(Exception exception) {

    log.error("Handling exception {}.", exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(ApiExceptionModel.builder()
            .timestamp(OffsetDateTime.now(ZoneId.systemDefault()))
            .message(exception.getMessage())
            .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .build());
  }

  private ResponseEntity<ApiExceptionModel> getBadRequestMessage(String message) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(ApiExceptionModel.builder()
            .timestamp(OffsetDateTime.now(ZoneId.systemDefault()))
            .message(message)
            .code(HttpStatus.BAD_REQUEST.name())
            .build());
  }

}
