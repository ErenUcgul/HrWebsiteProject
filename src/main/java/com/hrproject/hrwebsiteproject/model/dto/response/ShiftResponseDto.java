package com.hrproject.hrwebsiteproject.model.dto.response;

import java.time.LocalTime;

public record ShiftResponseDto(
        Long id,
        String shiftName,
        LocalTime beginHour,
        LocalTime endHour
) {
}
