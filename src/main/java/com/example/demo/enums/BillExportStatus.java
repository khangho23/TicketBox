package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BillExportStatus {
    FAIL(0),
    SUCCESS(1),
    PENDING(2),
    TIME_OUT(3);

    @Getter
    private int value;
}

