package com.dkuz.bonus.service.impl;

import com.dkuz.bonus.dto.BalanceResponseDto;
import com.dkuz.bonus.dto.enums.BalanceOperationType;
import com.dkuz.bonus.model.Balance;
import com.dkuz.bonus.model.User;
import com.dkuz.bonus.repository.BalanceRepository;
import com.dkuz.bonus.repository.UserRepository;
import com.dkuz.bonus.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;

    @Override
    public BalanceResponseDto getBalance(UUID userId) {
        return balanceRepository.findByUserId(userId).map(
                        balance -> new BalanceResponseDto(balance.getCount(), userId, BalanceOperationType.GET))
                .orElse(new BalanceResponseDto(null, userId, BalanceOperationType.NOT_FOUND));
    }

    @Override
    public BalanceResponseDto addCountBonus(UUID userId, Integer countBonus) {
        User user = getUser(userId);

        balanceRepository.findByUserId(userId).ifPresentOrElse(
                balance -> {
                    balance.setCount(balance.getCount() + countBonus);
                    balanceRepository.save(balance);
                },
                () -> balanceRepository.save(new Balance(user, countBonus)));
        return new BalanceResponseDto(null, userId, BalanceOperationType.ADD);
    }

    @Override
    public BalanceResponseDto debitBonus(UUID userId, Integer countBonus) {
        getUser(userId);

        return balanceRepository.findByUserId(userId).map(balance -> {
            if (balance.getCount() < countBonus) {
                return (new BalanceResponseDto(null, userId, BalanceOperationType.NOT_ENOUGH_BONUS));
            }

            int count = balance.getCount() - countBonus;
            if (count <= 0) {
                count = 0;
            }

            balance.setCount(count);
            balanceRepository.save(balance);
            return new BalanceResponseDto(null, userId, BalanceOperationType.DEBIT);
        }).orElse(new BalanceResponseDto(null, userId, BalanceOperationType.NOT_ENOUGH_BONUS));
    }

    private User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
