package com.hrproject.hrwebsiteproject.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import com.hrproject.hrwebsiteproject.model.enums.EMaterialStatus;
import jakarta.validation.constraints.NotNull;

public record MaterialRequestDto(
        @NotBlank(message = "Materyal adı boş olamaz.") String name,
        String brand,
        String model,
        String serialNumber,
        String description,
        @NotNull(message = "Materyal durumu boş olamaz.") EMaterialStatus status
) {}
