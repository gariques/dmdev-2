package com.iddev.enums;

import lombok.Getter;

public enum CarBrand {
    VOLKSWAGEN("Volkswagen"),
    TOYOTA("Toyota"),
    MERCEDES_BENZ("Mercedes-Benz"),
    FORD("Ford"),
    HONDA("Honda"),
    HYUNDAI("Hyundai"),
    BMW("BMW"),
    NISSAN("Nissan"),
    LEXUS("Lexus");

    @Getter
    private final String displayBrand;

    CarBrand(String displayBrand) {
        this.displayBrand = displayBrand;
    }
}
