package com.stockcontent.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    VIDEO_EDITING("Видеомонтаж"),
    PHOTO("Фото"),
    ARTICLES("Статьи"),
    OTHER("Другое");
    private final String name;
}

