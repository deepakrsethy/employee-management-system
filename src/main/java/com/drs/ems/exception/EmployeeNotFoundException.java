package com.drs.ems.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String employeeId) {
        super("Employee Not found for employeeId: "+employeeId);
    }

    public EmployeeNotFoundException(String departmentName, Double minSalary) {
        super("Employee Not found for for search criteria departmentName: "+departmentName+" and Minimum Salary: "+minSalary);
    }
}
