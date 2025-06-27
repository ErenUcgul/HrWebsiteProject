package com.hrproject.hrwebsiteproject.model.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record MyShiftsResponseDto(
        Long shiftId,
        String shiftName,
        LocalTime beginHour,
        LocalTime endHour,
        LocalDate startDate,
        LocalDate endDate
) {
}
