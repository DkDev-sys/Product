package com.dkuz.bonus.repository;

import com.dkuz.bonus.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {
    Optional<Balance> findByUserId(UUID userId);
}
