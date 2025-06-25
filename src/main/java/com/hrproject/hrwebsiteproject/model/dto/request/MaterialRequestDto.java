package com.hrproject.hrwebsiteproject.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MaterialRequestDto(
        @NotBlank(message = "Materyal adı boş olamaz.") String name,
        String brand,
        String model,
        String serialNumber,
        String description
) {}
