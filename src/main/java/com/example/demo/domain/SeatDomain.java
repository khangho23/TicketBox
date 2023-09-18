package com.example.demo.domain;

import org.seasar.doma.Domain;

import lombok.Getter;

@Domain(valueType = String.class, factoryMethod = "of")
public class SeatDomain {
    @Getter
    private final String value;
    private SeatDomain(String value) {
        this.value = value;
    }

    public static SeatDomain of(String value) {
        return new SeatDomain(value);
    }
}
