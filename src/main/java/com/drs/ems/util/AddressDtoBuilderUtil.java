package com.drs.ems.util;

import com.drs.ems.entity.Employee;
import com.drs.ems.exception.AddressNotFoundException;
import com.drs.ems.model.AddressResponse;

import java.util.Objects;

public class AddressDtoBuilderUtil {
    public static AddressResponse buildAddressResponseFromEmployee(Employee employee) {

        if(Objects.nonNull(employee.getAddress())) {
            return   AddressResponse.builder()
                    .user(employee.getName())
                    .addressLine1(employee.getAddress().getAddressLine1())
                    .addressLine2(employee.getAddress().getAddressLine2())
                    .city(employee.getAddress().getCity())
                    .state(employee.getAddress().getState())
                    .country(employee.getAddress().getCountry())
                    .pinCode(employee.getAddress().getPinCode())
                    .build();
        } else {
            throw  new AddressNotFoundException(employee.getId());
        }
    }
}
