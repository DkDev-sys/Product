package com.dkuz.bonus.service.impl;

import com.dkuz.bonus.dto.BalanceRequestDto;
import com.dkuz.bonus.dto.BalanceResponseDto;
import com.dkuz.bonus.dto.UserDto;
import com.dkuz.bonus.dto.enums.BalanceOperationType;
import com.dkuz.bonus.model.Balance;
import com.dkuz.bonus.model.User;
import com.dkuz.bonus.repository.BalanceRepository;
import com.dkuz.bonus.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceImplTest {

    @InjectMocks
    private BalanceServiceImpl balanceService;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BalanceRepository balanceRepository;

    private UUID userId;
    private BalanceRequestDto requestDto;

    @BeforeEach
    void init() {
        balanceService = new BalanceServiceImpl(userRepository, balanceRepository);
        userService = new UserServiceImpl(userRepository, balanceRepository);
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("1");
        user.setSurname("1");

        Mockito.lenient().when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDto userDto = new UserDto();
        userDto.setName("1");
        userDto.setSurname("1");

        userId = userService.createUser(userDto).getId();

        requestDto = new BalanceRequestDto(userId, 90);

        Balance balance = new Balance();
        balance.setId(UUID.randomUUID());
        balance.setCount(100);
        balance.setUser(user);

        Mockito.lenient().when(balanceRepository.findByUserId(userId)).thenReturn(Optional.of(balance));
    }

    @Test
    void getBalance() {
        BalanceResponseDto balance = balanceService.getBalance(userId);

        assertNotNull(balance);
        assertEquals(requestDto.getUserId(), balance.getUserId());
        assertEquals(100, balance.getBalance());

    }

    @Test
    void addCountBonus() {
        BalanceResponseDto balance = balanceService.addCountBonus(requestDto.getUserId(), requestDto.getCountBonus());

        assertNotNull(balance);
        assertEquals(requestDto.getUserId(), balance.getUserId());
        assertEquals(190, balanceService.getBalance(userId).getBalance());

        assertThrows(RuntimeException.class, () -> balanceService.debitBonus(UUID.randomUUID(), requestDto.getCountBonus()));
    }

    @Test
    void debitBonus() {
        BalanceResponseDto balance = balanceService.debitBonus(requestDto.getUserId(), requestDto.getCountBonus());

        assertNotNull(balance);
        assertEquals(requestDto.getUserId(), balance.getUserId());
        assertEquals(10, balanceService.getBalance(userId).getBalance());

        assertThrows(RuntimeException.class, () -> balanceService.debitBonus(UUID.randomUUID(), requestDto.getCountBonus()));
    }

    @Test
    void debitBonusOver() {
        BalanceResponseDto balance = balanceService.debitBonus(requestDto.getUserId(), 300);

        assertNotNull(balance);
        assertEquals(requestDto.getUserId(), balance.getUserId());
        assertEquals(BalanceOperationType.NOT_ENOUGH_BONUS, balance.getOperationType());
    }
}