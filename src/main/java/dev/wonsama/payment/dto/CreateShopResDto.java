package dev.wonsama.payment.dto;

import java.time.LocalDateTime;

import dev.wonsama.payment.entity.Shop;

import lombok.Getter;

@Getter
public class CreateShopResDto {
  private String id;
  private String shopName;
  private String ssno;
  private LocalDateTime createdAt;

  public CreateShopResDto(Shop shop) {
    this.id = shop.getId();
    this.shopName = shop.getShopName();
    this.ssno = shop.getSsno();
    this.createdAt = shop.getCreatedAt();
  }
}
