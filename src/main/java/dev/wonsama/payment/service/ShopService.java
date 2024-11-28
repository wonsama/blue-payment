package dev.wonsama.payment.service;

import dev.wonsama.payment.dto.CreateShopReqDto;
import dev.wonsama.payment.entity.Shop;

/**
 * 가맹점 서비스
 */

public interface ShopService {

  /**
   * 가맹점 등록
   *
   * @param dto 가맹점 등록 요청 DTO
   * @return 가맹점 정보
   */
  public Shop createShop(CreateShopReqDto dto);

}
