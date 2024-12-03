package dev.wonsama.payment.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.wonsama.payment.dto.CreateShopReqDto;
import dev.wonsama.payment.dto.CreateShopResDto;
import dev.wonsama.payment.entity.Shop;
import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import dev.wonsama.payment.exception.PaymentSystemException;

import dev.wonsama.payment.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payment/shop")
@Tag(name = "Shop", description = "Shop 관련 API")
public class ShopController {

  @Autowired
  private ShopService shopService;

  @PostMapping
  @Operation(summary = "가맹점(Shop) 생성", description = "가맹점(Shop) 정보를 생성합니다.")
  public CreateShopResDto createShop(@Valid @RequestBody CreateShopReqDto dto, BindingResult bindingResult) {

    log.info("1.1. /api/payment/shop : ", ToStringBuilder.reflectionToString(dto));

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(error -> {
        log.error("Error: {}", error.getDefaultMessage());
      });
      throw new PaymentSystemException(PaymentSystemErrorCode.INVALID_REQUEST_PARAMETER);
    }

    Shop shop = shopService.createShop(dto);
    return new CreateShopResDto(shop);
  }
}
