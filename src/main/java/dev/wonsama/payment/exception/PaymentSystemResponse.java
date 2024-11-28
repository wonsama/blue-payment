package dev.wonsama.payment.exception;

import org.springframework.http.ResponseEntity;

import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentSystemResponse {

  private int status;
  private String code;
  private String message;

  public static ResponseEntity<PaymentSystemResponse> toResponseEntity(PaymentSystemErrorCode e) {
    return ResponseEntity
        .status(e.getHttpStatus())
        .body(PaymentSystemResponse.builder()
            .status(e.getHttpStatus().value())
            .code(e.name())
            .message(e.getMessage())
            .build());
  }

}
