package com.hrproject.hrwebsiteproject.model.dto.request;

public record DeleteEmbezzlementRequestDto(
        String token,
        Long userId
) {}
