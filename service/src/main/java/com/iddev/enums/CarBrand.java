package com.iddev.enums;

public enum CarBrand {
    VOLKSWAGEN ("Volkswagen"),
    TOYOTA ("Toyota"),
    MERCEDES_BENZ ("Mercedes-Benz"),
    FORD ("Ford"),
    HONDA ("Honda"),
    HYUNDAI ("Hyundai"),
    BMW ("BMW"),
    NISSAN ("Nissan"),
    LEXUS ("Lexus");

    private final String displayBrand;

    CarBrand(String displayBrand) {
        this.displayBrand = displayBrand;
    }

    public String getDisplayBrand() {
        return displayBrand;
    }
}
