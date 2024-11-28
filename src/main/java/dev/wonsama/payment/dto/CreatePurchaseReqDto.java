package dev.wonsama.payment.dto;

import com.google.gson.Gson;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePurchaseReqDto {

  @NotNull(message = "가맹점 정보는 필수 입력 정보입니다")
  private String shopId;

  @NotNull(message = "카드 정보는 필수 입력 정보입니다")
  private String cardId;

  @Min(value = 100, message = "가격은 100원 이상으로 입력해주세요")
  private int price;

  @Min(value = 1, message = "2개월 이상으로 입력해주세요. 1=일시불")
  @Max(value = 36, message = "0-36개월로 입력해주세요")
  private int installmentMonth;

  private String tokenId;

  /**
   * 생성자
   * 
   * @param shopId            가맹점 아이디
   * @param cardId            카드 아이디
   * @param price             가격
   * @param installmentMonths 할부개월수
   */
  @Builder
  public CreatePurchaseReqDto(String shopId, String cardId, int price, int installmentMonth) {
    this.shopId = shopId;
    this.cardId = cardId;
    this.price = price;
    this.installmentMonth = installmentMonth;
  }

  /**
   * JSON 문자열로 변환
   */
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
