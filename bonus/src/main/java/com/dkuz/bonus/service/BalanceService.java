package com.dkuz.bonus.service;

import com.dkuz.bonus.dto.BalanceResponseDto;

import java.util.UUID;

public interface BalanceService {
    BalanceResponseDto getBalance(UUID id);

    BalanceResponseDto addCountBonus(UUID userId, Integer countBonus);

    BalanceResponseDto debitBonus(UUID userId, Integer countBonus);
}
