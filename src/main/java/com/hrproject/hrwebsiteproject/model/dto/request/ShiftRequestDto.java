package com.hrproject.hrwebsiteproject.model.dto.request;

import java.time.LocalTime;
public record ShiftRequestDto(
        String shiftName,
        LocalTime shiftStart,
        LocalTime shiftEnd,
        Long companyId)
{
}
