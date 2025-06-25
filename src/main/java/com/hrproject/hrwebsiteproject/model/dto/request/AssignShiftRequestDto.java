package com.hrproject.hrwebsiteproject.model.dto.request;

import java.time.LocalDate;

public record AssignShiftRequestDto(
        Long shiftId,
        LocalDate startDate,
        LocalDate endDate
)
{
}
