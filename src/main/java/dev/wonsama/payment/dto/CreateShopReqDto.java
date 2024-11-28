package dev.wonsama.payment.dto;

import org.hibernate.validator.constraints.Length;

import dev.wonsama.payment.entity.Shop;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateShopReqDto {

  @NotNull(message = "매장명을 입력 바랍니다.")
  private String shopName;

  @Length(min = 10, max = 10, message = "사업자번호는 10자리로 입력해주세요.")
  private String ssno;

  @Builder
  public CreateShopReqDto(String shopName, String ssno) {
    this.shopName = shopName;
    this.ssno = ssno;
  }

  public Shop toEntity() {
    return Shop.builder()
        .shopName(shopName)
        .ssno(ssno)
        .useYn(true)
        .build();
  }

}
