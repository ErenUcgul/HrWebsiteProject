package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.EMaterialStatus;

public record MaterialResponseDto(
        Long id,
        String name,
        String brand,
        String model,
        String serialNumber,
        String description,
        EMaterialStatus status
) {}
