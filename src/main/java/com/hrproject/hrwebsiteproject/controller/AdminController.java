package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.AdminDashboardResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.service.AdminService;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.StaticContentService;
import com.hrproject.hrwebsiteproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //Admin dashboard buraya gelecek
    @GetMapping(EndPoints.ADMIN_DASHBOARD)
    public ResponseEntity<BaseResponse<AdminDashboardResponse>> getAdminDashboard() {
        AdminDashboardResponse dto = AdminDashboardResponse.builder()
                .totalUsers(userService.countAll())
                .totalCompanies(companyService.countAll())
                .pendingCompanyApprovals(companyService.countPending())
                .build();

        return ResponseEntity.ok(BaseResponse.<AdminDashboardResponse>builder()
                .code(200)
                .success(true)
                .message("Admin dashboard verileri getirildi.")
                .data(dto)
                .build());
    }

}
