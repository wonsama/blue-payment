package dev.wonsama.payment.exception;

import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentSystemException extends RuntimeException {
  PaymentSystemErrorCode errorCode;
}
