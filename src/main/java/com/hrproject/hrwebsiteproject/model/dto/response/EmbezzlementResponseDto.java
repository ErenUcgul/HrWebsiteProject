package com.hrproject.hrwebsiteproject.model.dto.response;

import java.time.LocalDateTime;

public record EmbezzlementResponseDto(
        Long id,
        Long materialId,
        Long userId,
        LocalDateTime assignedAt,
        Boolean isReturned,        // Yeni: iade edildi mi?
        LocalDateTime returnDate, // Yeni: iade tarihi (null olabilir)
        boolean active
) {}