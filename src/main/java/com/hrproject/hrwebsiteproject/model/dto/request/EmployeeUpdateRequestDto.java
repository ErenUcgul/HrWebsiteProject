package com.hrproject.hrwebsiteproject.model.dto.request;

import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EPosition;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EmployeeUpdateRequestDto(
//        @NotNull(message = "Employee ID boş olamaz")
//        Long employeeId,

        String firstName,

        String lastName,

        String phone,

        String avatar,

        String gender,

        String userRole,


        @Size(min = 11, max = 11, message = "TCKN 11 haneli olmalıdır.")
        String identityNo,


        @Past(message = "Doğum tarihi geçmiş bir tarih olmalıdır.")
        LocalDate birthDate,

        String address,


        EPosition position,


        LocalDate dateOfEmployment,

        LocalDate dateOfTermination,


        @Positive(message = "Maaş pozitif bir değer olmalıdır.")
        Double salary,


        String socialSecurityNumber,


        EEmploymentStatus employmentStatus
) {
}
