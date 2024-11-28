package dev.wonsama.payment.service;

import dev.wonsama.payment.dto.CreatePurchaseReqDto;
import dev.wonsama.payment.dto.CreatePurchaseResDto;

/**
 * 구매 서비스
 */
public interface PurchaseService {

  /**
   * 구매 처리 등록
   *
   * @param dto 가맹점 등록 요청 DTO
   * @return 가맹점 정보
   */
  public CreatePurchaseResDto createPurchase(CreatePurchaseReqDto dto);
}
