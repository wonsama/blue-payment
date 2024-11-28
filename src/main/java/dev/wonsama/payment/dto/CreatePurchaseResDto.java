package dev.wonsama.payment.dto;

import lombok.Getter;

@Getter
public class CreatePurchaseResDto {

  private String approvalNo;

  public CreatePurchaseResDto(String approvalNo) {
    this.approvalNo = approvalNo;
  }
}
