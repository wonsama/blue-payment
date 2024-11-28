package dev.wonsama.payment.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.wonsama.payment.dto.CreatePurchaseReqDto;
import dev.wonsama.payment.dto.CreatePurchaseResDto;
import dev.wonsama.payment.dto.HttpResponseDto;
import dev.wonsama.payment.entity.Card;
import dev.wonsama.payment.entity.Purchase;
import dev.wonsama.payment.entity.Shop;
import dev.wonsama.payment.enums.PaymentSystemErrorCode;
import dev.wonsama.payment.exception.PaymentSystemException;
import dev.wonsama.payment.repository.CardRepository;
import dev.wonsama.payment.repository.PurchaseRepository;
import dev.wonsama.payment.repository.ShopRepository;
import dev.wonsama.payment.service.PurchaseService;
import dev.wonsama.payment.util.HttpUtil;
import dev.wonsama.payment.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 구매 서비스
 */
@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private ShopRepository shopRepository;

  @Autowired
  private HttpUtil httpUtil;

  @Autowired
  private JsonUtil jsonUtil;

  @Value("${api.issue.host}")
  private String API_ISSUE_HOST;

  @Value("${api.issue.path.token}")
  private String API_ISSUE_PATH_TOKEN;

  @Value("${api.auth.host}")
  private String API_AUTH_HOST;

  @Value("${api.auth.path.token.verify}")
  private String API_AUTH_PATH_TOKEN_VERIFY;

  @Override
  public CreatePurchaseResDto createPurchase(CreatePurchaseReqDto dto) {

    // 1. 구매 정보 생성
    Purchase purchase = createPurchaseStep1(dto);

    // 2. 토큰서버에서 토큰 생성 - 구매 정보 내 토큰 정보 업데이트
    String tokenId = createPurchaseStep2(dto);
    log.info("created tokenId: {}", tokenId);

    // 3. 인증 서버에서 토큰 검증 - 구매 정보 내 승인번호 업데이트
    dto.setTokenId(tokenId);
    String approvalNo = createPurchaseStep3(dto);

    // 4. 구매 정보 업데이트
    purchase.setApprovalNo(approvalNo);
    purchaseRepository.save(purchase);
    return new CreatePurchaseResDto(approvalNo);
  }

  /**
   * 토큰 검증
   * 
   * @param dto 구매 요청 정보
   * @return 승인번호
   */
  private String createPurchaseStep3(CreatePurchaseReqDto dto) {
    // Auth 을 통한 토큰 검증
    HttpResponseDto res = null;
    try {
      res = httpUtil.postJson(String.format("%s%s", API_AUTH_HOST, API_AUTH_PATH_TOKEN_VERIFY),
          dto.toJson());
    } catch (IOException e) {
      log.error("interface call failed {}", dto.toJson());
      throw new PaymentSystemException(PaymentSystemErrorCode.IF_AUTH_SYSTEM_TOKEN_VERIFY);
    }

    // Auth 응답코드 확인
    if (res.getStatus() != 200) {
      log.error("interface call failed {} {}", res.getStatus(), res.getContent());
      throw new PaymentSystemException(PaymentSystemErrorCode.IF_AUTH_SYSTEM_TOKEN_VERIFY);
    }

    // Auth 응답정보 파싱
    boolean valid = jsonUtil.getBoolean(res.getContent(), "valid");
    String cause = jsonUtil.getString(res.getContent(), "cause");
    String approvalNo = jsonUtil.getString(res.getContent(), "approvalNo");

    if (!valid) {
      log.error("interface call failed {}", cause);
      throw new PaymentSystemException(PaymentSystemErrorCode.IF_AUTH_SYSTEM_TOKEN_VERIFY);
    }

    // 승인번호 반환
    return approvalNo;
  }

  /**
   * 토큰 발행
   * 
   * @param dto 구매 요청 정보
   * @return 토큰 정보
   */
  private String createPurchaseStep2(CreatePurchaseReqDto dto) {
    // TokenSystem 을 통한 토큰 발행
    HttpResponseDto res = null;
    try {
      res = httpUtil.postJson(String.format("%s%s", API_ISSUE_HOST, API_ISSUE_PATH_TOKEN),
          dto.toJson());
    } catch (IOException e) {
      log.error("interface call failed {}", dto.toJson());
      throw new PaymentSystemException(PaymentSystemErrorCode.IF_TOKEN_SYSTEM_TOKEN_ISSUE);
    }

    // 응답코드 확인
    if (res.getStatus() != 200) {
      log.error("interface call failed {} {}", res.getStatus(), res.getContent());
      throw new PaymentSystemException(PaymentSystemErrorCode.IF_TOKEN_SYSTEM_TOKEN_ISSUE);
    }

    // 토큰 정보 반환
    return jsonUtil.getString(res.getContent(), "id");
  }

  /**
   * 구매 정보 생성
   * 
   * @param dto 구매 요청 정보
   * @return 구매 정보
   */
  private Purchase createPurchaseStep1(CreatePurchaseReqDto dto) {
    // 1.1 카드 정보 조회
    Card card = cardRepository.findById(dto.getCardId())
        .orElseThrow(() -> {
          log.error("Card not found. cardId: {}", dto.getCardId());
          return new PaymentSystemException(PaymentSystemErrorCode.CARD_NOT_FOUND);
        });
    // 1.2 가맹점 정보 조회
    Shop shop = shopRepository.findById(dto.getShopId())
        .orElseThrow(() -> {
          log.error("Shop not found. cardId: {}", dto.getShopId());
          return new PaymentSystemException(PaymentSystemErrorCode.SHOP_NOT_FOUND);
        });
    // 1.3 구매 정보 저장
    Purchase purchase = Purchase.builder()
        .shop(shop)
        .card(card)
        .price(dto.getPrice())
        .installmentMonth(dto.getInstallmentMonth()).build();
    purchaseRepository.save(purchase);
    return purchase;
  }
}
