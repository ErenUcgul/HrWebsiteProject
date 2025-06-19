package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EPosition;

import java.time.LocalDate;

public record EmployeeResponseDto(
        Long userId,
        String identityNo,
        LocalDate birthDate,
        String address,
        EPosition position,
        LocalDate dateOfEmployment,
        LocalDate dateOfTermination,
        Double salary,
        EEmploymentStatus employmentStatus,
        String socialSecurityNumber
) {
}
