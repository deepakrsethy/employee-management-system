package com.drs.ems.util;

import com.drs.ems.entity.Employee;
import com.drs.ems.model.EmployeeResponse;

import java.util.List;

public class EmployeeDtoBuilderUtil {
    public static EmployeeResponse buildResponseFromEntity(Employee employee) {
        new EmployeeResponse();
        return EmployeeResponse.builder()
                .Id(String.valueOf(employee.getId()))
                .name(employee.getName())
                .email(employee.getEmail())
                .gender(employee.getGender())
                .phoneNumber(employee.getPhoneNumber())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartment().getId())
                .joiningDate(employee.getJoiningDate())
                .addressId(employee.getAddress().getId().toString())
                .build();
    }

    public static List<EmployeeResponse> buildResponseFromEntity(List<Employee> employees) {
        return employees.stream().map(EmployeeDtoBuilderUtil::buildResponseFromEntity).toList();
    }
}
