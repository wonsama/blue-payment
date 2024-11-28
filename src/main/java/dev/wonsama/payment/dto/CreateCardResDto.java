package dev.wonsama.payment.dto;

import java.time.LocalDateTime;

import dev.wonsama.payment.entity.Card;
import lombok.Getter;

@Getter
public class CreateCardResDto {

  private String id;
  private String cardNoMask;
  private String cardName;
  private LocalDateTime createdAt;

  public CreateCardResDto(Card card) {
    this.id = card.getId();
    this.cardNoMask = card.getCardNoMask();
    this.cardName = card.getCardName();
    this.createdAt = card.getCreatedAt();
  }
}
