package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
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
import com.hrproject.hrwebsiteproject.util.JwtManager;
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
    private final JwtManager jwtManager;

    @PutMapping(EndPoints.APPROVE_USER_AND_COMPANY)
    public ResponseEntity<BaseResponse<Boolean>> approveUserAndCompany(
            @RequestHeader String token,
            @RequestParam Long userId) {

        Long performedByUserId = jwtManager.getUserIdFromToken(token);

        adminService.approveUserAndCompany(userId, performedByUserId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Kullanıcı ve şirket aktif hale getirildi.")
                .data(true)
                .build());
    }

    //Anasayfa içeriklerini güncellemek için kullanılır
    @PutMapping(EndPoints.UPDATE_CONTENT)
    public ResponseEntity<BaseResponse<Boolean>> updateStaticContent(
            @RequestHeader String token,
            @RequestParam String key,
            @RequestBody String newContent) {


        Long userId = jwtManager.getUserIdFromToken(token);

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
    public ResponseEntity<BaseResponse<AdminDashboardResponse>> getAdminDashboard(
            @RequestHeader String token) {

        // Opsiyonel: Giriş yapan adminin ID’sini çıkarabiliriz
        Long adminId = jwtManager.getUserIdFromToken(token);

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
    public ResponseEntity<BaseResponse<Boolean>> rejectUserAndCompany(@RequestParam Long companyId,
                                                                      @RequestHeader String token) {

        // Giriş yapan adminin ID’sini almak için (log veya audit için kullanılabilir)
        Long adminId = jwtManager.getUserIdFromToken(token);

        // Admin yetkisi JWT ile kontrol ediliyor varsayımıyla doğrudan işleme geçiyoruz
        adminService.rejectCompanyById(companyId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Kullanıcı ve şirket reddedildi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.LIST_COMPANIES_BY_STATES)
    public ResponseEntity<BaseResponse<List<CompanyStateInfoResponse>>> getAllCompaniesWithStateInfo(
            @RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        List<CompanyStateInfoResponse> list = companyService.getAllCompaniesWithStateInfo();

        return ResponseEntity.ok(BaseResponse.<List<CompanyStateInfoResponse>>builder()
                .code(200)
                .success(true)
                .message("Şirketlerin state bilgileri listelendi.")
                .data(list)
                .build());
    }

    @GetMapping(EndPoints.USER_STATE_LIST)
    public ResponseEntity<BaseResponse<List<UserStateInfoResponse>>> getAllUsersWithStateInfo(
            @RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        List<UserStateInfoResponse> list = userService.getAllUsersWithStateInfo();

        return ResponseEntity.ok(BaseResponse.<List<UserStateInfoResponse>>builder()
                .code(200)
                .success(true)
                .message("Kullanıcıların state bilgileri listelendi.")
                .data(list)
                .build());
    }

    @GetMapping(EndPoints.USER_STATE_PENDING)
    public ResponseEntity<List<UserStateInfoResponse>> getPendingUsers(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(userService.getUsersByState(EUserState.PENDING));
    }

    @GetMapping(EndPoints.USER_STATE_IN_REVIEW)
    public ResponseEntity<List<UserStateInfoResponse>> getInReviewUsers(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(userService.getUsersByState(EUserState.IN_REVIEW));
    }

    @GetMapping(EndPoints.USER_STATE_ACTIVE)
    public ResponseEntity<List<UserStateInfoResponse>> getActiveUsers(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(userService.getUsersByState(EUserState.ACTIVE));
    }

    @GetMapping(EndPoints.USER_STATE_INACTIVE)
    public ResponseEntity<List<UserStateInfoResponse>> getInactiveUsers(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(userService.getUsersByState(EUserState.INACTIVE));
    }

    @GetMapping(EndPoints.USER_STATE_REJECTED)
    public ResponseEntity<List<UserStateInfoResponse>> getRejectedUsers(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(userService.getUsersByState(EUserState.REJECTED));
    }

    @GetMapping(EndPoints.COMPANY_STATE_PENDING)
    public ResponseEntity<List<CompanyStateInfoResponse>> getPendingCompanies(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.PENDING));
    }

    @GetMapping(EndPoints.COMPANY_STATE_IN_REVIEW)
    public ResponseEntity<List<CompanyStateInfoResponse>> getInReviewCompanies(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.IN_REVIEW));
    }

    @GetMapping(EndPoints.COMPANY_STATE_ACTIVE)
    public ResponseEntity<List<CompanyStateInfoResponse>> getActiveCompanies(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.ACTIVE));
    }

    @GetMapping(EndPoints.COMPANY_STATE_REJECTED)
    public ResponseEntity<List<CompanyStateInfoResponse>> getRejectedCompanies(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.REJECTED));
    }

    @GetMapping(EndPoints.COMPANY_STATE_INACTIVE)
    public ResponseEntity<List<CompanyStateInfoResponse>> getInactiveCompanies(@RequestHeader String token) {
        Long adminId = jwtManager.getUserIdFromToken(token);

        return ResponseEntity.ok(companyService.getCompaniesByState(ECompanyState.INACTIVE));
    }
}
