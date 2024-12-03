package dev.wonsama.payment.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.wonsama.payment.dto.CreatePurchaseReqDto;
import dev.wonsama.payment.dto.CreatePurchaseResDto;
import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import dev.wonsama.payment.exception.PaymentSystemException;
import dev.wonsama.payment.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payment/purchase")
@Tag(name = "Purchase", description = "Purchase 관련 API")
public class PurchaseController {

  @Autowired
  private PurchaseService purchaseService;

  @PostMapping
  @Operation(summary = "물건 구매 수행", description = "구매 정보 생성 및 토큰서버에서 토큰 생성 이후 구매 인증 시스템에서 해당 토큰 검증 후 해당 구매 건에 대한 승인이 이뤄집니다.")
  public CreatePurchaseResDto createPurchase(@Valid @RequestBody CreatePurchaseReqDto dto,
      BindingResult bindingResult) {

    log.info("🟢 1.3. /api/payment/purchase : ", ToStringBuilder.reflectionToString(dto));

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(error -> {
        log.error("Error: {}", error.getDefaultMessage());
      });
      throw new PaymentSystemException(PaymentSystemErrorCode.INVALID_REQUEST_PARAMETER);
    }

    return purchaseService.createPurchase(dto);
  }

}
