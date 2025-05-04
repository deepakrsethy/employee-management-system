package com.drs.ems.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long employeeId) {
        super("Address not found Employee Id:"+employeeId);
    }
}
