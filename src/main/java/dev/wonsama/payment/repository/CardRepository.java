package dev.wonsama.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.wonsama.payment.entity.Card;

public interface CardRepository extends JpaRepository<Card, String> {

}
