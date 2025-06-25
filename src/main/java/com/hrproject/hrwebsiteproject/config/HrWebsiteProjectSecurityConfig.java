package com.hrproject.hrwebsiteproject.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
public class HrWebsiteProjectSecurityConfig {
    @Bean
    public JwtTokenFilter getJwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("securityFilterChain Devrede!!!");
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests

                        .requestMatchers("/swagger-ui/**", "/api-docs/**",
                                "/v1/dev/auth/register",
                                "v1/dev/auth/verify-email", "v1/dev/admin/approve-user-and-company", "v1/dev/auth/login",
                                "v1/dev/auth/forgot-password", "v1/dev/auth/reset-password",
                                "v1/dev/user/profile", "v1/dev/user/change-email", "v1/dev/user/change-password", "/v1/dev/user/deactivate-account",
                                "/v1/dev/publicapi/homepage-content",
                                "/v1/dev/publicapi/how-it-works",
                                "/v1/dev/publicapi/platform-features",
                                "/v1/dev/publicapi/holidays",
                                "/v1/dev/admin/update-content",
                                "v1/dev/auth/refresh-access-token",
                                "v1/dev/admin/dashboard",
                                "v1/dev/company/dashboard", "v1/dev/employee/dashboard",
                                "v1/dev/admin/reject-company",
                                "v1/dev/admin/list-companies-by-states",
                                "v1/dev/admin/user-state-list",
                                "v1/dev/admin/user-state-pending",
                                "v1/dev/admin/user-state-in-review",
                                "v1/dev/admin/user-state-active",
                                "v1/dev/admin/user-state-inactive",
                                "v1/dev/admin/user-state-rejected",
                                "v1/dev/admin/company-state-pending",
                                "v1/dev/admin/company-state-in-review",
                                "v1/dev/admin/company-state-active",
                                "v1/dev/admin/company-state-rejected",
                                "v1/dev/admin/company-state-inactive",
                                "v1/dev/employee/create-employee",
                                "v1/dev/employee/update-employee",
                                "v1/dev/employee/delete-employee",
                                "v1/dev/employee/deactivate-employee",
                                "v1/dev/employee/activate-employee",
                                "v1/dev/employee/list-all-employees",
                                "v1/dev/employee/get-employee-details",
                                "v1/dev/leave-type/create-leave-type",
                                "v1/dev/leave-type/update-leave-type",
                                "v1/dev/leave-type/delete-leave-type",
                                "v1/dev/leave-type/list-leave-type",
                                "v1/dev/company-leave-type/assign-leave-type",
                                "v1/dev/company-leave-type/assign-leave-type",
                                "v1/dev/employee-leave-controller/employee-leave-request",
                                "v1/dev/company-leave-type/approve-or-reject-leave",
                                "v1/dev/file/upload-pdf",
                                "v1/dev/file/upload-pdf-jpg",
                                "v1/dev/file/files/list",
                                "v1/dev/file/files/download",
                                "v1/dev/file/files/delete",
                                "v1/dev/shift/create-shift",
                                "/v1/dev/shift/list-shifts",
                                "/v1/dev/shift/delete-shift",
                                "v1/dev/shift/update-shift",
                                "v1/dev/shift/assign-shift",
                                "v1/dev/shift/update-shift-assign",
                                "v1/dev/shift/delete-shift-assign",
                                "v1/dev/shift/get-shift-by-employee",
                                "/v1/dev/shift/get-employee-by-shift",
                                "v1/dev/employee-expense-controller/create-expense",
                                "v1/dev/employee-expense-controller/my-expenses",
                                "v1/dev/salary-payment-controller/generate-salary",
                                "v1/dev/employee-expense-controller/update-expense",
                                "v1/dev/employee-expense-controller/delete-expense",
                                "v1/dev/employee-expense-controller/approved-expenses",
                                "v1/dev/employee-expense-controller/rejected-expenses",
                                "v1/dev/employee-expense-controller/pending-expenses",
                                "v1/dev/employee-expense-controller/list-all-expenses",
                                "v1/dev/embezzlement/delete-by-user",
                                "v1/dev/embezzlement/my-list",
                                "v1/dev/embezzlement/assign",
                                "v1/dev/embezzlement/list",
                                "v1/dev/embezzlement/add",
                                "v1/dev/material/create",
                                "v1/dev/material/list",
                                "v1/dev/embezzlement/return-embezzlement",
                                "v1/dev/company-review/create-company-review",
                                "v1/dev/company-review/list-approved-company-reviews",
                                "v1/dev/company-review/list-pending-company-reviews",
                                "v1/dev/company-review/approve-or-reject-company-review",
                                "v1/dev/company-review/delete-company-review",
                                "v1/dev/company-review/update-company-review",
                                "v1/dev/company-review/list-all-company-reviews"
                        ).permitAll()

                        .requestMatchers("/admin/**",
                                "v1/dev/admin/approve-user-and-company",
                                "v1/dev/publicapi/homepage-content",
                                "v1/dev/publicapi/how-it-works",
                                "v1/dev/publicapi/platform-features",
                                "v1/dev/publicapi/holidays",
                                "v1/dev/admin/update-content",
                                "v1/dev/admin/dashboard",
                                "v1/dev/admin/reject-company",
                                "v1/dev/admin/list-companies-by-states",
                                "v1/dev/admin/user-state-list",
                                "v1/dev/admin/user-state-pending",
                                "v1/dev/admin/user-state-in-review",
                                "v1/dev/admin/user-state-active",
                                "v1/dev/admin/user-state-inactive",
                                "v1/dev/admin/user-state-rejected",
                                "v1/dev/admin/company-state-pending",
                                "v1/dev/admin/company-state-in-review",
                                "v1/dev/admin/company-state-active",
                                "v1/dev/admin/company-state-rejected",
                                "v1/dev/admin/company-state-inactive",
                                "v1/dev/leave-type/create-leave-type",
                                "v1/dev/leave-type/update-leave-type",
                                "v1/dev/leave-type/delete-leave-type",
                                "v1/dev/leave-type/list-leave-type",
                                "v1/dev/auth/verify-email",
                                "v1/dev/auth/login",
                                "v1/dev/auth/logout",
                                "v1/dev/auth/forgot-password",
                                "v1/dev/auth/reset-password",
                                "v1/dev/user/profile",
                                "v1/dev/user/change-email",
                                "v1/dev/user/change-password",
                                "/v1/dev/user/deactivate-account"

                        ).hasAuthority("ADMIN")

                        .requestMatchers("/manager/**",
                                "v1/dev/auth/verify-email",
                                "v1/dev/auth/login",
                                "v1/dev/auth/logout",
                                "v1/dev/auth/forgot-password",
                                "v1/dev/auth/reset-password",
                                "/v1/dev/auth/register",
                                "v1/dev/user/profile",
                                "v1/dev/user/change-email",
                                "v1/dev/user/change-password",
                                "/v1/dev/user/deactivate-account",
                                "v1/dev/user/profile", // update
                                "v1/dev/user/confirm-change-email",
                                "v1/dev/auth/refresh-access-token",
                                "v1/dev/company/dashboard",
                                "v1/dev/employee/dashboard",
                                "v1/dev/employee/create-employee",
                                "v1/dev/employee/update-employee",
                                "v1/dev/employee/delete-employee",
                                "v1/dev/employee/deactivate-employee",
                                "v1/dev/employee/activate-employee",
                                "v1/dev/employee/list-all-employees",
                                "v1/dev/employee/get-employee-details",
                                "v1/dev/company-leave-type/assign-leave-type",
                                "v1/dev/company-leave-type/assign-leave-type",
                                "v1/dev/company-leave-type/approve-or-reject-leave",
                                "v1/dev/company-leave-type/list-company-leave-type",
                                "v1/dev/shift/create-shift",
                                "/v1/dev/shift/list-shifts",
                                "/v1/dev/shift/delete-shift",
                                "v1/dev/shift/update-shift",
                                "v1/dev/shift/assign-shift",
                                "v1/dev/shift/update-shift-assign",
                                "v1/dev/shift/delete-shift-assign",
                                "v1/dev/shift/get-shift-by-employee",
                                "/v1/dev/shift/get-employee-by-shift"

                        ).hasAuthority("MANAGER")
                        .requestMatchers("/employee/**",

                                "v1/dev/auth/login",
                                "v1/dev/auth/logout",
                                "v1/dev/auth/forgot-password",
                                "v1/dev/auth/reset-password",
                                "v1/dev/auth/refresh-access-token",
                                "v1/dev/user/profile",
                                "v1/dev/user/change-email",
                                "v1/dev/user/change-password",
                                "v1/dev/user/profile", // update
                                "v1/dev/user/confirm-change-email",
                                "v1/dev/employee-leave-controller/employee-leave-request",
                                "v1/dev/employee/get-employee-details",
                                "v1/dev/employee/dashboard",
                                "v1/dev/company-leave-type/list-company-leave-type"

                        ).hasAuthority("EMPLOYEE")
                        .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}