package com.bcnc.prices.web.exception;

import com.bcnc.prices.web.model.ErrorCode;
import com.bcnc.prices.web.model.ErrorRs;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<ErrorRs> methodArgumentNotValidException(Exception exception) {
    String field = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldError().getField();
    String message = "'" + field + "' field is required";
    ErrorRs errorRs = ErrorRs.builder()
        .message(message)
        .errorCode(ErrorCode.REQUIRED_FIELD)
        .build();
    return ResponseEntity.badRequest().body(errorRs);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseBody
  public ResponseEntity<ErrorRs> httpMessageNotReadableException(Exception exception) {
    ErrorRs errorRs = ErrorRs.builder()
        .message(exception.getMessage())
        .errorCode(ErrorCode.DESERIALIZATION_ERROR)
        .build();
    return ResponseEntity.badRequest().body(errorRs);
  }
}
