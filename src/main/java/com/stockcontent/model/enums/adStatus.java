package com.stockcontent.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum adStatus {
    WAITING("Ожидание"),
    ACTIVE("Актуально"),
    SOLD("Не актуально"),
    REFUSED("Отказано");
    private final String name;
}

