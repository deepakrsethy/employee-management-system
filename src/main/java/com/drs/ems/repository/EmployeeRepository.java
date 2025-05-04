package com.drs.ems.repository;

import com.drs.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findTopByOrderBySalaryDesc();

    Optional<Employee> findTopByOrderBySalaryAsc();
}
