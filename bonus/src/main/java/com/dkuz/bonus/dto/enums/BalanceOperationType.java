package com.dkuz.bonus.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor()
/*можно добавить текст для ошибок вторым аргуметом*/
public enum BalanceOperationType {
    GET(200),
    ADD(200),
    DEBIT(200),
    NOT_ENOUGH_BONUS(400),
    NOT_FOUND(404);

    private final Integer code;
}
