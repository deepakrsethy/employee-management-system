package com.drs.ems.controller;

import com.drs.ems.model.EmployeeResponse;
import com.drs.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/{employeeId}")
    public EmployeeResponse getEmployee(@PathVariable Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    //use case 1: Filtering Employees**
    //**Requirement:** Find all employees in a department X with salary > Y.

    /**
     * /employees?department=Engineering&minSalary=50000
     *
     * /api/employees?department=HR
     *
     * /api/employees?minSalary=60000
     *
     * @param departmentName
     * @param minSalary
     * @return
     */
    @GetMapping
    public List<EmployeeResponse> getEmployees(
            @RequestParam(name = "department", required = false) String departmentName,
            @RequestParam(name = "minSalary", required = false) Double minSalary) {
        return employeeService.getEmployees(departmentName, minSalary);
    }


}
