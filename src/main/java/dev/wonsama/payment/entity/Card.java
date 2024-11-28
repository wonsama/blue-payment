package dev.wonsama.payment.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 등록된 카드 정보 엔티티
 */
@Entity
@Getter
@Setter
@Table(name = "tb_card")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Card {

  /**
   * 카드 아이디 - TokenSystem 에서 생성된 카드 아이디를 사용한다
   */
  @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  /**
   * 마스킹 된 카드 번호
   */
  @Column(nullable = false, name = "card_no_mask")
  private String cardNoMask;

  /**
   * 카드사명
   */
  @Column(name = "card_name")
  private String cardName; // ENUM 으로 관리하기엔 종류가 많아 일단 String 으로 관리

  /**
   * 등록일
   */
  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @OneToMany
  @JoinColumn(name = "card_id")
  List<Purchase> purchases;

  /**
   * 카드 정보 생성자
   * 
   * @param id         카드 아이디 - TokenSystem 에서 생성된 카드 아이디를 사용한다
   * @param cardNoMask 마스킹 된 카드 번호
   * @param cardName   카드사명
   */
  @Builder
  Card(String id, String cardNoMask, String cardName) {
    this.id = id;
    this.cardNoMask = cardNoMask;
    this.cardName = cardName;
  }
}
