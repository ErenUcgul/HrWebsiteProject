package com.hrproject.hrwebsiteproject.model.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record MyShiftResponseDto(
        String shiftName,
        LocalTime beginHour,
        LocalTime endHour,
        LocalDate beginDate,
        LocalDate endDate
) {
}
