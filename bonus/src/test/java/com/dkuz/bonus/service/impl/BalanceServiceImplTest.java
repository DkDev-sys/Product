package com.dkuz.bonus.service.impl;

import com.dkuz.bonus.dto.BalanceRequestDto;
import com.dkuz.bonus.dto.BalanceResponseDto;
import com.dkuz.bonus.dto.UserDto;
import com.dkuz.bonus.model.User;
import com.dkuz.bonus.repository.BalanceRepository;
import com.dkuz.bonus.repository.UserRepository;
import com.dkuz.bonus.service.BalanceService;
import com.dkuz.bonus.service.UserService;
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
        userService = new UserServiceImpl(userRepository);
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

        requestDto = new BalanceRequestDto(userId, 100);

        balanceService.addCountBonus(userId, 100);
    }

    @Test
    void getBalance() {
        BalanceResponseDto balance = balanceService.getBalance(userId);

        assertNotNull(balance);
        assertEquals(requestDto.getUserId(), balance.getUserId());
        assertEquals(requestDto.getCountBonus(), 100);
    }

    @Test
    void addCountBonus() {
        BalanceResponseDto balance = balanceService.addCountBonus(userId, 100);

        assertNotNull(balance);
        assertEquals(requestDto.getUserId(), balance.getUserId());
    }

    @Test
    void debitBonus() {
        BalanceResponseDto balance = balanceService.getBalance(userId);

        assertNotNull(balance);
        assertEquals(requestDto.getUserId(), balance.getUserId());
    }
}