package dev.wonsama.payment.dto;

import org.hibernate.validator.constraints.Length;

import com.google.gson.Gson;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCardReqDto {

  @NotNull(message = "CI 정보는 필수 입력 정보입니다")
  private String ci;

  @Length(min = 15, max = 16, message = "카드번호는 15자 이상 16자 이하로 입력해주세요.")
  private String cardNo;

  @Builder
  public CreateCardReqDto(String ci, String cardNo) {
    this.ci = ci;
    this.cardNo = cardNo;
  }

  /**
   * JSON 문자열로 변환
   */
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
