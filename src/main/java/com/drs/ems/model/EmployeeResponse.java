package com.drs.ems.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private String Id;

    private String name;

    private String designation;

    private String gender;

    private String email;

    private Long departmentId;

    private Double salary;

    private LocalDate joiningDate;

    private String phoneNumber;

    private String addressId;
}
