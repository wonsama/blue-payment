package dev.wonsama.payment.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.wonsama.payment.dto.CreateCardReqDto;
import dev.wonsama.payment.dto.CreateCardResDto;
import dev.wonsama.payment.entity.Card;
import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import dev.wonsama.payment.exception.PaymentSystemException;
import dev.wonsama.payment.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * 카드 관련 API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/payment/card")
@Tag(name = "Card", description = "Card 관련 API")
public class CardController {

  @Autowired
  private CardService cardService;

  /**
   * 카드 등록
   * 
   * @param dto           카드 등록 요청 DTO
   * @param bindingResult 바인딩 결과
   * @return 등록된 카드 정보
   */
  @PostMapping
  @Operation(summary = "Card 생성", description = "Card 정보를 생성합니다.")
  public CreateCardResDto createCard(@Valid @RequestBody CreateCardReqDto dto, BindingResult bindingResult) {

    log.info("🟢 1.2. /api/payment/card : ", ToStringBuilder.reflectionToString(dto));

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(error -> {
        log.error("Error: {}", error.getDefaultMessage());
      });
      throw new PaymentSystemException(PaymentSystemErrorCode.INVALID_REQUEST_PARAMETER);
    }

    Card card = cardService.createCard(dto);
    return new CreateCardResDto(card);
  }
}
