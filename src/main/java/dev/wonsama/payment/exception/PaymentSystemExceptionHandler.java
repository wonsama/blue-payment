package dev.wonsama.payment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentSystemExceptionHandler {
  @ExceptionHandler(PaymentSystemException.class)
  protected ResponseEntity<PaymentSystemResponse> handleCustomException(PaymentSystemException e) {
    return PaymentSystemResponse.toResponseEntity(e.getErrorCode());
  }
}
