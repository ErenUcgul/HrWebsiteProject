package com.hrproject.hrwebsiteproject.util;

import com.hrproject.hrwebsiteproject.model.dto.request.RegisterRequestDto;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyType;
import com.hrproject.hrwebsiteproject.model.enums.ERegion;
import com.hrproject.hrwebsiteproject.model.enums.Egender;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import com.hrproject.hrwebsiteproject.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyDataInitializer {

    private final RegistrationService registrationService;
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        RegisterRequestDto company1 = new RegisterRequestDto(
                "Ahmet",
                "Yılmaz",
                "Password123!",
                "Password123!",
                "eren.ucgul@bilgeadamboost.com",
                "05551234567",
                null,
                Egender.MALE,
                "BilgeAdamB",
                "İstanbul, Türkiye",
                ERegion.TURKEY,
                "02123334455",
                "info@bilgeadam.com",
                null,
                ECompanyType.TECHNOLOGY
        );

        RegisterRequestDto company2 = new RegisterRequestDto(
                "Ayşe",
                "Kara",
                "StrongPass1@",
                "StrongPass1@",
                "basak.ozek@bilgeadamboost.com",
                "05557654321",
                null,
                Egender.FEMALE,
                "BilgeAdamBo",
                "Ankara, Türkiye",
                ERegion.TURKEY,
                "03121234567",
                "contact@healthcareplus.com",
                null,
                ECompanyType.HEALTHCARE
        );

        RegisterRequestDto company3 = new RegisterRequestDto(
                "Mehmet",
                "Demir",
                "SecurePass9#",
                "SecurePass9#",
                "mert.sari@bilgeadamboost.com",
                "05559876543",
                null,
                Egender.MALE,
                "BilgeAdamBoo",
                "İzmir, Türkiye",
                ERegion.TURKEY,
                "02321239876",
                "info@constructpro.com",
                null,
                ECompanyType.CONSTRUCTION
        );


        try {
            registrationService.registerWithCompany(company2);
            System.out.println("Company2 registered");
        } catch (Exception e) {
            System.out.println("Failed to register company2: " + e.getMessage());
        }

        try {
            registrationService.registerWithCompany(company1);
            System.out.println("Company1 registered");
        } catch (Exception e) {
            System.out.println("Failed to register company1: " + e.getMessage());
        }

        try {
            registrationService.registerWithCompany(company3);
            System.out.println("Company3 registered");
        } catch (Exception e) {
            System.out.println("Failed to register company3: " + e.getMessage());
        }
    }
}
