package dev.wonsama.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.wonsama.payment.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {

}
