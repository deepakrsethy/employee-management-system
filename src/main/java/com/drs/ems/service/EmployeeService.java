package com.drs.ems.service;

import com.drs.ems.entity.Employee;
import com.drs.ems.enums.DepartmentNames;
import com.drs.ems.exception.EmployeeNotFoundException;
import com.drs.ems.model.AddressResponse;
import com.drs.ems.model.DepartmentResponse;
import com.drs.ems.model.EmployeeResponse;
import com.drs.ems.repository.EmployeeRepository;
import com.drs.ems.util.AddressDtoBuilderUtil;
import com.drs.ems.util.EmployeeDtoBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponse getEmployee(Long employeeId) {
        return employeeRepository
                .findById(employeeId)
                .map(EmployeeDtoBuilderUtil::buildResponseFromEntity)
                .orElseThrow(() -> new EmployeeNotFoundException(String.valueOf(employeeId)));
    }

    public List<EmployeeResponse> getEmployees(String departmentName, Double minSalary) {
        return employeeRepository.findAll().stream()
                .map(EmployeeDtoBuilderUtil::buildResponseFromEntity)
                .filter(employee ->
                        employee.getDepartmentName().equalsIgnoreCase(
                                DepartmentNames.fromName(departmentName).getName())
                                &&
                                employee.getSalary() > minSalary
                ).toList();
    }

    public EmployeeResponse getEmployeeWithMaxSalary() {
        return employeeRepository.findAll().stream()
                .max(Comparator.comparing(Employee::getSalary))
                .map(EmployeeDtoBuilderUtil::buildResponseFromEntity)
                .orElseThrow(() -> new RuntimeException("Could not get employee with max salary"));
    }

    public EmployeeResponse getEmployeeWithMinSalary() {
        return employeeRepository.findTopByOrderBySalaryAsc()
                .map(EmployeeDtoBuilderUtil::buildResponseFromEntity)
                .orElseThrow(() -> new RuntimeException("Could not get employee with min salary"));
    }

    public Map<String, List<EmployeeResponse>> getEmployeesGroupedByDepartment() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = employees.stream().map(EmployeeDtoBuilderUtil::buildResponseFromEntity).toList();
        return employeeResponses.stream()
                .collect(
                        Collectors.groupingBy(
                                EmployeeResponse::getDepartmentName));
    }

    public AddressResponse getEmployeeAddress(String employeeId) {
        Employee employee = employeeRepository.findById(Long.parseLong(employeeId))
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return AddressDtoBuilderUtil.buildAddressResponseFromEmployee(employee);

    }

    public Map<String, Double> getAvgSalaryPerDepartment() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDtoBuilderUtil::buildResponseFromEntity)
                .collect(
                        Collectors.groupingBy(
                                EmployeeResponse::getDepartmentName,
                                Collectors.averagingDouble(EmployeeResponse::getSalary)
                        )
                );
    }

    public List<EmployeeResponse> filterAndGetEmployeesBasedOnJoiningDate(int joiningYear, boolean isAfter) {

        return employeeRepository.findAll().stream()
                .map(EmployeeDtoBuilderUtil::buildResponseFromEntity)
                .filter(
                        employeeResponse ->
                                isAfter
                                        ? employeeResponse.getJoiningDate().isAfter(LocalDate.of(joiningYear, 1, 1))
                                        : employeeResponse.getJoiningDate().isBefore(LocalDate.of(joiningYear, 1, 1)))
                .collect(Collectors.toList());
    }

}
