package dev.wonsama.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.wonsama.payment.dto.CreateShopReqDto;
import dev.wonsama.payment.entity.Shop;
import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import dev.wonsama.payment.exception.PaymentSystemException;
import dev.wonsama.payment.repository.ShopRepository;
import dev.wonsama.payment.service.ShopService;
import lombok.extern.slf4j.Slf4j;

/**
 * 가맹점 서비스
 */
@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

  @Autowired
  private ShopRepository shopRepository;

  /**
   * 가맹점 등록
   *
   * @param dto 가맹점 등록 요청 DTO
   * @return 가맹점 정보
   */
  @Override
  public Shop createShop(CreateShopReqDto dto) {

    // 가맹점 기등록 검증
    shopRepository.findBySsno(dto.getSsno()).ifPresent(c -> {
      log.error("Shop already registered. ssno: {}", dto.getSsno());
      throw new PaymentSystemException(PaymentSystemErrorCode.ALREADY_REGISTERED_SHOP);
    });

    // 가맹점 등록 처리
    return shopRepository.save(dto.toEntity());
  }

}
