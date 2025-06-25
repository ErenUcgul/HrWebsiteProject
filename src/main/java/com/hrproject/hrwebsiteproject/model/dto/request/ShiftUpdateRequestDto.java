package com.hrproject.hrwebsiteproject.model.dto.request;

import java.time.LocalTime;

public record ShiftUpdateRequestDto(
        String shiftName,
        LocalTime shiftStart,
        LocalTime shiftEnd
) {
}
