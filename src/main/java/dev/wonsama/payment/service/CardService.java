package dev.wonsama.payment.service;

import dev.wonsama.payment.dto.CreateCardReqDto;
import dev.wonsama.payment.entity.Card;

/**
 * 카드 서비스
 */
public interface CardService {

  /**
   * 카드 정보를 저장한다
   * 
   * @param dto 카드 정보
   * @return 저장된 카드 정보
   */
  public Card createCard(CreateCardReqDto dto);
}
