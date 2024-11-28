package dev.wonsama.payment.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import dev.wonsama.payment.enums.PurchaseStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 구매 정보 엔티티
 */
@Entity
@Getter
@Setter
@Table(name = "tb_purchase")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {

  /**
   * 구매 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  /**
   * 가격
   */
  @Column(nullable = false, name = "price")
  private int price;

  /**
   * 처리 상태 : SUCCESS, FAIL, CANCEL, ING
   */
  @Column(nullable = false, name = "status")
  @Enumerated(EnumType.STRING)
  private PurchaseStatus status;

  /**
   * 할부개월수 - 0 이면 일시불, (2~36)
   */
  @Column(nullable = false, name = "installment_month")
  private int installmentMonth;

  /**
   * 승인번호 - 카드사에서 발급한 승인번호, 최초 생성시 null - 이때 상태는 START
   */
  @Column(name = "approval_no")
  private String approvalNo;

  /**
   * 등록일
   */
  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**
   * 수정일
   */
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne
  private Card card;

  @ManyToOne
  private Shop shop;

  @Builder
  public Purchase(Card card, Shop shop, int price, int installmentMonth) {
    this.card = card;
    this.shop = shop;
    this.price = price;
    this.installmentMonth = installmentMonth;
    this.status = PurchaseStatus.ING;
  }
}
