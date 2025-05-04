package com.drs.ems.exception;

public class UnknownJoiningYearConditionFilterException extends RuntimeException {
    public UnknownJoiningYearConditionFilterException(String joiningCondition) {
        super(String.format("Unknown joining condition: %s . Use before or after",joiningCondition));
    }
}
