package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.AdminDashboardResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyStateInfoResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.UserStateInfoResponse;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.service.AdminService;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.StaticContentService;
import com.hrproject.hrwebsiteproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.ADMIN)
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService;
    private final StaticContentService staticContentService;
    private final UserService userService;
    private final CompanyService companyService;

    @PutMapping(EndPoints.APPROVE_USER_AND_COMPANY)
    public ResponseEntity<BaseResponse<Boolean>> approveUserAndCompany(Long userId) {
        adminService.approveUserAndCompany(userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Kullanıcı ve şirket aktif hale getirildi.")
                .data(true)
                .build());
    }

    //Anasayfa içeriklerini güncellemek için kullanılır
    @PutMapping(EndPoints.UPDATE_CONTENT)
    public ResponseEntity<BaseResponse<Boolean>> updateStaticContent(@RequestParam String key,
                                                                     @RequestBody String newContent) {
        staticContentService.updateContent(key, newContent);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("İçerik güncellendi.")
                .data(true)
                .build());
    }

    //Admin dashboard
    @GetMapping(EndPoints.ADMIN_DASHBOARD)
    public ResponseEntity<BaseResponse<AdminDashboardResponse>> getAdminDashboard() {
        AdminDashboardResponse dto = AdminDashboardResponse.builder()
                .totalUsers(userService.countAll())
                .activeUsers(userService.countByState(EUserState.ACTIVE))
                .pendingUsers(userService.countByState(EUserState.PENDING))
                .rejectedUsers(userService.countByState(EUserState.REJECTED))
                .inReviewUsers(userService.countByState(EUserState.IN_REVIEW))

                .totalCompanies(companyService.countAll())
                .activeCompanies(companyService.countByState(ECompanyState.ACTIVE))
                .pendingCompanies(companyService.countByState(ECompanyState.PENDING))
                .rejectedCompanies(companyService.countByState(ECompanyState.REJECTED))
                .inReviewCompanies(companyService.countByState(ECompanyState.IN_REVIEW))

                .usersRegisteredLast7Days(userService.countUsersRegisteredLast7Days())
                .companiesRegisteredLast7Days(companyService.countCompaniesRegisteredLast7Days())
                .build();

        return ResponseEntity.ok(BaseResponse.<AdminDashboardResponse>builder()
                .code(200)
                .success(true)
                .message("Admin dashboard verileri getirildi.")
                .data(dto)
                .build());
    }

    @PutMapping(EndPoints.REJECT_COMPANY)
    public ResponseEntity<BaseResponse<Boolean>> rejectUserAndCompany(@RequestParam Long companyId) {
        adminService.rejectCompanyById(companyId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Kullanıcı ve şirket reddedildi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.LIST_COMPANIES_BY_STATES)
    public ResponseEntity<BaseResponse<List<CompanyStateInfoResponse>>> getAllCompaniesWithStateInfo() {
        List<CompanyStateInfoResponse> list = companyService.getAllCompaniesWithStateInfo();

        return ResponseEntity.ok(BaseResponse.<List<CompanyStateInfoResponse>>builder()
                .code(200)
                .success(true)
                .message("Şirketlerin state bilgileri listelendi.")
                .data(list)
                .build());
    }
    @GetMapping(EndPoints.USER_STATE_LIST)
    public ResponseEntity<BaseResponse<List<UserStateInfoResponse>>> getAllUsersWithStateInfo() {
        List<UserStateInfoResponse> list = userService.getAllUsersWithStateInfo();

        return ResponseEntity.ok(BaseResponse.<List<UserStateInfoResponse>>builder()
                .code(200)
                .success(true)
                .message("Kullanıcıların state bilgileri listelendi.")
                .data(list)
                .build());
    }

    @GetMapping(EndPoints.USER_STATE_PENDING)
    public ResponseEntity<List<UserStateInfoResponse>> getPendingUsers() {
        return ResponseEntity.ok(userService.getUsersByState(EUserState.PENDING));
    }

    @GetMapping(EndPoints.USER_STATE_IN_REVIEW)
    public ResponseEntity<List<UserStateInfoResponse>> getInReviewUsers() {
        return ResponseEntity.ok(userService.getUsersByState(EUserState.IN_REVIEW));
    }

    @GetMapping(EndPoints.USER_STATE_ACTIVE)
    public ResponseEntity<List<UserStateInfoResponse>> getActiveUsers() {
        return ResponseEntity.ok(userService.getUsersByState(EUserState.ACTIVE));
    }

    @GetMapping(EndPoints.USER_STATE_INACTIVE)
    public ResponseEntity<List<UserStateInfoResponse>> getInactiveUsers() {
        return ResponseEntity.ok(userService.getUsersByState(EUserState.INACTIVE));
    }

    @GetMapping(EndPoints.USER_STATE_REJECTED)
    public ResponseEntity<List<UserStateInfoResponse>> getRejectedUsers() {
        return ResponseEntity.ok(userService.getUsersByState(EUserState.REJECTED));
    }

    @GetMapping(EndPoints.COMPANY_STATE_PENDING)
    public ResponseEntity<List<CompanyStateInfoResponse>> getPendingCompanies() {
        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.PENDING));
    }

    @GetMapping(EndPoints.COMPANY_STATE_IN_REVIEW)
    public ResponseEntity<List<CompanyStateInfoResponse>> getInReviewCompanies() {
        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.IN_REVIEW));
    }

    @GetMapping(EndPoints.COMPANY_STATE_ACTIVE)
    public ResponseEntity<List<CompanyStateInfoResponse>> getActiveCompanies() {
        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.ACTIVE));
    }

    @GetMapping(EndPoints.COMPANY_STATE_REJECTED)
    public ResponseEntity<List<CompanyStateInfoResponse>> getRejectedCompanies() {
        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.REJECTED));
    }
    @GetMapping(EndPoints.COMPANY_STATE_INACTIVE)
    public ResponseEntity<List<CompanyStateInfoResponse>> getInactiveCompanies() {
        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.INACTIVE));
    }
}
