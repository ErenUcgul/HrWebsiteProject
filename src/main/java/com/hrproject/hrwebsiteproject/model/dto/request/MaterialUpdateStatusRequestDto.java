package com.hrproject.hrwebsiteproject.model.dto.request;

import com.hrproject.hrwebsiteproject.model.enums.EMaterialStatus;
import jakarta.validation.constraints.NotNull;

public record MaterialUpdateStatusRequestDto(
        @NotNull(message = "Materyal ID boş olamaz") Long materialId,
        @NotNull(message = "Durum alanı boş olamaz") EMaterialStatus status
) {}
