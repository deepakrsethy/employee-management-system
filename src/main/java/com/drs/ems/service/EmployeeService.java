package com.drs.ems.service;

import com.drs.ems.entity.Employee;
import com.drs.ems.enums.DepartmentNames;
import com.drs.ems.exception.EmployeeNotFoundException;
import com.drs.ems.model.EmployeeResponse;
import com.drs.ems.repository.EmployeeRepository;
import com.drs.ems.util.EmployeeDtoBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponse getEmployee(Long employeeId) {
        Employee employee = employeeRepository
                    .findById(employeeId)
                    .orElseThrow(() ->new EmployeeNotFoundException(String.valueOf(employeeId)));
        EmployeeResponse response = EmployeeDtoBuilderUtil.buildResponseFromEntity(employee);
        return response;
    }

    public List<EmployeeResponse> getEmployees(String departmentName, Double minSalary) {
        List<Employee> employees = employeeRepository.findAll();
       // List<Employee> filteredWithJava7 = filterEmployeesUsingJava7(employees, departmentName, minSalary);

        List<Employee> filteredWithJavStream = filterEmployeesUsingStream(employees, departmentName, minSalary);
        List<EmployeeResponse> employeeResponses = EmployeeDtoBuilderUtil.buildResponseFromEntity(filteredWithJavStream);
        if (employeeResponses.isEmpty()) {
            throw new EmployeeNotFoundException(departmentName, minSalary);
        } else {
            return employeeResponses;
        }
    }

    private List<Employee> filterEmployeesUsingStream(List<Employee> employees, String departmentName, Double minSalary) {
        long startTime = System.currentTimeMillis();
        List<Employee> filteredEmployees = employees.stream()
                .filter(employee ->
                        employee.getDepartment().getDepartmentName().equalsIgnoreCase(
                                DepartmentNames.fromName(departmentName).getName())
                                &&
                                employee.getSalary() > minSalary
                ).toList();
        log.info("Total time taken to filter employees using Stream API: {} MilliSecs" ,(System.currentTimeMillis() - startTime));
        return filteredEmployees;
    }

    private List<Employee> filterEmployeesUsingJava7(List<Employee> employees, String departmentName, Double minSalary) {
        long startTime = System.currentTimeMillis();
        List<Employee> filteredEmployees = new ArrayList<>();
        for(Employee e: employees) {
            if(e.getDepartment().getDepartmentName().equalsIgnoreCase(
                    DepartmentNames.fromName(departmentName).getName())
                && e.getSalary() > minSalary
            ) {
                filteredEmployees.add(e);
            }
        }
        log.info("Total time taken to filter employees using java 7: {} MilliSecs" ,(System.currentTimeMillis() - startTime));
        return filteredEmployees;

    }
}
