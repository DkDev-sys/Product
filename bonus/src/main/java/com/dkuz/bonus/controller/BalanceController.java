package com.dkuz.bonus.controller;

import com.dkuz.bonus.dto.BalanceRequestDto;
import com.dkuz.bonus.dto.BalanceResponseDto;
import com.dkuz.bonus.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
@RequestMapping("balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping()
    public ResponseEntity<BalanceResponseDto> getBalance(@RequestParam UUID userId) {
        BalanceResponseDto balance = balanceService.getBalance(userId);
        return ResponseEntity.status(balance.getOperationType().getCode()).body(balance);
    }

    /*Не совсем понял по заданию, договоривались без авторизации и секьюрности, поэтому формулировка,
    доступно только ограниченому количеству потребителей сервиса не совсем понятна*/
    @PostMapping("/add_count")
    public ResponseEntity<BalanceResponseDto> addCountBonus(@RequestBody BalanceRequestDto request) {
        BalanceResponseDto balance = balanceService.addCountBonus(request.getUserId(), request.getCountBonus());
        return ResponseEntity.status(balance.getOperationType().getCode()).body(balance);
    }

    @PostMapping("/debit_bonus")
    public ResponseEntity<BalanceResponseDto> debitBonus(@RequestBody BalanceRequestDto request) {
        BalanceResponseDto balance = balanceService.debitBonus(request.getUserId(), request.getCountBonus());
        return ResponseEntity.status(balance.getOperationType().getCode()).body(balance);
    }
}
