package com.hrproject.hrwebsiteproject.model.dto.request;
import jakarta.validation.constraints.NotBlank;

public record LeaveTypeUpdateRequestDto(

        @NotBlank(message = "İzin türü adı boş olamaz.")
        String name,
        String description
) {
}
