package com.hrproject.hrwebsiteproject.util;

import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EPosition;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.Egender;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EmployeeDataInitializer {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // Örnek olarak "BilgeAdamB" şirketinin ID'sini alıyoruz
        Long companyId = 2L;


        EmployeeCreateRequestDto employee1 = EmployeeCreateRequestDto.builder()
                .firstName("Eren")
                .lastName("Üçgül")
                .phone("05559887768")
                .email("eren@bilgeadamboost.com")
                .avatar(null)
                .gender(Egender.MALE)
                .userRole(EUserRole.EMPLOYEE)
                .identityNo("12345678901")
                .birthDate(LocalDate.of(1980, 5, 20))
                .address("İstanbul")
                .position(EPosition.MID_LEVEL)
                .dateOfEmployment(LocalDate.of(2000, 1, 1))
                .dateOfTermination(null)
                .salary(12000.0)
                .employmentStatus(EEmploymentStatus.ACTIVE)
                .socialSecurityNumber("SGK123456789")
                .build();

        employeeService.createEmployee(employee1, companyId);

        EmployeeCreateRequestDto employee2 = EmployeeCreateRequestDto.builder()
                .firstName("Başak")
                .lastName("Özek")
                .phone("05559887766")
                .email("basak@bilgeadamboost.com")
                .avatar(null)
                .gender(Egender.FEMALE)
                .userRole(EUserRole.EMPLOYEE)
                .identityNo("12345678912")
                .birthDate(LocalDate.of(2000, 5, 20))
                .address("Ankara")
                .position(EPosition.DIRECTOR)
                .dateOfEmployment(LocalDate.of(2023, 1, 1))
                .dateOfTermination(null)
                .salary(12000.0)
                .employmentStatus(EEmploymentStatus.ACTIVE)
                .socialSecurityNumber("SGK123456790")
                .build();

        employeeService.createEmployee(employee2, companyId);

        // Başka employee eklenmek istenirse buraya eklenebilir
    }
}
