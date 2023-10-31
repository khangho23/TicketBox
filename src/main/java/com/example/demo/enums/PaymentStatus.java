package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentStatus {
    PENDING(0),
    FAILD(1),
    SUCCESS(2);

    @Getter
    private int value;
}

