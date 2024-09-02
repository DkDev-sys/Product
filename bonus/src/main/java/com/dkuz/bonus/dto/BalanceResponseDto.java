package com.dkuz.bonus.dto;

import com.dkuz.bonus.dto.enums.BalanceOperationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer balance;
    private UUID userId;
    private BalanceOperationType operationType;

}
