package com.hrproject.hrwebsiteproject.model.dto.response;

import java.time.LocalDateTime;

public record PersonalEmbezzlementResponseDto(
        Long id,
        Long materialId,
        LocalDateTime assignedAt
) {}
