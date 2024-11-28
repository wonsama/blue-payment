package dev.wonsama.payment.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.wonsama.payment.dto.HttpResponseDto;
import dev.wonsama.payment.dto.CreateCardReqDto;
import dev.wonsama.payment.entity.Card;
import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import dev.wonsama.payment.exception.PaymentSystemException;
import dev.wonsama.payment.repository.CardRepository;
import dev.wonsama.payment.service.CardService;
import dev.wonsama.payment.util.HttpUtil;
import dev.wonsama.payment.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private HttpUtil httpUtil;

  @Autowired
  private JsonUtil jsonUtil;

  @Value("${api.issue.host}")
  private String API_ISSUE_HOST;

  @Value("${api.issue.path.card}")
  private String API_ISSUE_PATH_CARD;

  @Override
  public Card createCard(CreateCardReqDto dto) {

    // TokenSystem 을 통한 카드 정보 등록 및 참조 아이디 반환
    HttpResponseDto res = null;
    try {
      res = httpUtil.postJson(String.format("%s%s", API_ISSUE_HOST, API_ISSUE_PATH_CARD),
          dto.toJson());
    } catch (IOException e) {
      log.error("interface call failed {}", dto.toJson());
      throw new PaymentSystemException(PaymentSystemErrorCode.IF_TOKEN_SYSTEM_CARD_REGIST);
    }

    // 응답코드 확인
    if (res.getStatus() != 200) {
      log.error("interface call failed {} {}", res.getStatus(), res.getContent());
      throw new PaymentSystemException(PaymentSystemErrorCode.IF_TOKEN_SYSTEM_CARD_REGIST);
    }

    // 카드 정보 등록
    String id = jsonUtil.getString(res.getContent(), "id");
    String cardNo = dto.getCardNo();
    String cardNoMask = getCardNoMask(cardNo);

    return cardRepository.save(Card.builder().id(id)
        .cardNoMask(cardNoMask)
        .cardName("카드사명") // 추후 발급서버에서 전달 받아야 됨
        .build());
  }

  /**
   * 카드번호 마스킹 처리
   * 
   * @param cardNo 카드번호
   * @return 마스킹된 카드번호
   */
  private String getCardNoMask(String cardNo) {
    return cardNo.substring(0, cardNo.length() - 4).replaceAll("[0-9]", "*") + cardNo.substring(cardNo.length() - 4);
  }

}
