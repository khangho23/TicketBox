package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum Role {
    EMPLOYEE(1),
    ADMIN(2);

    @Getter
    private final int value;

    private static final Map<Integer, Role> valueToRoleMap = new HashMap<>();

    static {
        for (Role role : Role.values()) {
            valueToRoleMap.put(role.getValue(), role);
        }
    }

    public static Role getRoleByValue(int value) {
        return valueToRoleMap.get(value);
    }
}
