package com.drs.ems.enums;

import java.util.Arrays;

public enum DepartmentNames {
    ENGINEERING("Engineering"),
    HUMAN_RESOURCE("Human Resources"),
    SALES("Sales"),
    MARKETING("Marketing"),
    PRODUCT_MANAGEMENT("Product Management");

    private final String name;

    DepartmentNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static DepartmentNames fromName(String name) {
        return Arrays.stream(DepartmentNames.values())
                .filter(d -> d.name.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid department: " + name));
    }
        
}
