package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EPosition;

public record EmployeeListDto(
        Long userId,
        String identityNo,
        EPosition position,
        EEmploymentStatus employmentStatus,
        String firstName,
        String lastName,
        String email
) {}
