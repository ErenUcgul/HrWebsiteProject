package com.hrproject.hrwebsiteproject.model.dto.response;

public record MaterialResponseDto(
        Long id,
        String name,
        String brand,
        String model,
        String serialNumber,
        String description
) {}
