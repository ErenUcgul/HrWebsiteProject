package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.*;

import java.time.LocalDate;

public record EmployeeDetailDto(
        Long userId,
        String identityNo,
        LocalDate birthDate,
        String address,
        EPosition position,
        LocalDate dateOfEmployment,
        LocalDate dateOfTermination,
        Double salary,
        EEmploymentStatus employmentStatus,
        String socialSecurityNumber,
        String firstName,
        String lastName,
        String email,
        String phone,
        String avatar,
        Egender gender,
        EUserState state
) {
}
