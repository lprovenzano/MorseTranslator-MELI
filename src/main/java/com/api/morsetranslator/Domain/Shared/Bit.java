package com.api.morsetranslator.Domain.Shared;

public enum Bit {
    PAUSE(0),
    PULSE(1);

    private int value;

    Bit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
