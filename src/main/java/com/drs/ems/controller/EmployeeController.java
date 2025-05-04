package com.drs.ems.controller;

import com.drs.ems.exception.InvalidInputException;
import com.drs.ems.exception.UnknownJoiningYearConditionFilterException;
import com.drs.ems.exception.UnknownSalaryFilterException;
import com.drs.ems.model.AddressResponse;
import com.drs.ems.model.EmployeeResponse;
import com.drs.ems.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long employeeId) {
        log.info("Fetching employee with ID: {}",employeeId);
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    //use case 1: Filtering Employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>>getEmployees(
            @RequestParam(name = "department", required = false) String departmentName,
            @RequestParam(name = "minSalary", required = false) Double minSalary) {
        log.info("Fetching all employees from Department: {} and with minimum salary: {}", departmentName, minSalary);
        return ResponseEntity.ok(employeeService.getEmployees(departmentName, minSalary));
    }

    //use case 2: Find Employee with max or min salary
    @GetMapping("/salary")
    public ResponseEntity<EmployeeResponse> getEmployeeWithExtremeSalary(@RequestParam String type) {
        log.info("Fetching employee with {} salary", type);
        return switch (type.toLowerCase()) {
            case "max" -> ResponseEntity.ok(employeeService.getEmployeeWithMaxSalary());
            case "min" -> ResponseEntity.ok(employeeService.getEmployeeWithMinSalary());
            default -> {
                log.warn("Unknown salary filter {} used to fetch employees with max or min salary", type);
                throw new UnknownSalaryFilterException(type);
            }
        };
    }

    //use case 3: Grouping employees by department name
    @GetMapping("/department/group")
    public ResponseEntity<Map<String,List<EmployeeResponse>>> getEmployeesGroupedByDepartment() {
        log.info("Fetching employees grouped by departments");
        return ResponseEntity.ok(employeeService.getEmployeesGroupedByDepartment());
    }

    //use case 4: Get employee address
    @GetMapping("/address/{employeeId}")
    public ResponseEntity<AddressResponse> getEmployeeAddress(@PathVariable String employeeId) {
        log.info("Fetching Address for employee with ID: {}",employeeId);
        return ResponseEntity.ok(employeeService.getEmployeeAddress(employeeId));
    }

    //use case 5: Avg Salary per department
    @GetMapping("/department/salary/avg")
    public ResponseEntity<Map<String, Double>> getAvgSalaryPerDepartment() {
        log.info("Fetching average salary grouped by department");
        return ResponseEntity.ok(employeeService.getAvgSalaryPerDepartment());
    }

    //Use case 6: Filter and get employees based on joining date
    @GetMapping("joining")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesAfterJoiningYear(
            @RequestParam(name = "joiningYear", required = false) Integer joiningYear,
            @RequestParam(name = "joiningCondition", required = false) String joiningCondition) {

        if (joiningYear == null || joiningCondition == null) {
            log.warn("Missing or invalid joiningYear/joiningCondition: {} / {}", joiningYear, joiningCondition);
            throw new InvalidInputException("Both 'joiningYear' and 'joiningCondition' must be provided.");
        }

        log.info("Filtering employees for joining date {} {}", joiningCondition, joiningYear);
        return switch (joiningCondition.toLowerCase()) {
            case "after" -> ResponseEntity.ok(employeeService.filterAndGetEmployeesBasedOnJoiningDate(joiningYear, true));
            case "before" -> ResponseEntity.ok(employeeService.filterAndGetEmployeesBasedOnJoiningDate(joiningYear,false));
            default -> {
                log.warn("Unknown filter: {} used for Filtering employees for joining date {}", joiningCondition, joiningYear);
                throw new UnknownJoiningYearConditionFilterException(joiningCondition);
            }
        };
    }

}
