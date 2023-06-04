package com.stockcontent.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Type {
    ENTERTAINMENT("Развлекательный"),
    TRAINING("Обучающий"),
    BUSINESS("Деловой");
    private final String name;
}

