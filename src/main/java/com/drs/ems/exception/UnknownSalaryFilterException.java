package com.drs.ems.exception;

public class UnknownSalaryFilterException extends RuntimeException {
    public UnknownSalaryFilterException(String type) {
        super(String.format("Invalid salary filter type: %s . Use max or min",type));
    }
}
