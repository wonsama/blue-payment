package dev.wonsama.payment.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 가맹점 정보 엔티티
 */
@Entity
@Getter
@Setter
@Table(name = "tb_shop")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Shop {

  /**
   * 가맹점 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  /**
   * 가맹점 명
   */
  @Column(nullable = false, name = "shop_name")
  private String shopName;

  /**
   * 사업자번호
   */
  @Column(nullable = false, unique = true, name = "ssno")
  private String ssno;

  /**
   * 사용여부
   */
  @Column(nullable = false, name = "use_yn")
  @ColumnDefault("true")
  private boolean useYn;

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

  @OneToMany
  @JoinColumn(name = "shop_id")
  List<Purchase> purchases;

  /**
   * 생성자
   * 
   * @param shopName 매장명
   * @param ssno     사업자번호
   * @param useYn    사용여부
   */
  @Builder
  public Shop(String shopName, String ssno, boolean useYn) {
    this.shopName = shopName;
    this.ssno = ssno;
    this.useYn = useYn;
  }
}
