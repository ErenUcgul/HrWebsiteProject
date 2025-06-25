package com.hrproject.hrwebsiteproject.model.dto.request;

public record AssigmentEmbezzlementRequestDto(
        String token,
        Long embezzlementId,
        Long userId
) {}
