package dev.wonsama.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.wonsama.payment.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, String> {

  @Query("SELECT s FROM Shop s WHERE s.ssno = :ssno")
  public Optional<Shop> findBySsno(@Param("ssno") String ssno);
}
