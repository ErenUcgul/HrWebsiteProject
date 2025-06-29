package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.entity.StaticContent;
import com.hrproject.hrwebsiteproject.service.StaticContentService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.PUBLIC_API)
@RequiredArgsConstructor
@CrossOrigin("*")
public class StaticContentController {
    private final StaticContentService staticContentService;

    private final JwtManager jwtManager;  // token manager eklenmeli

    @GetMapping(EndPoints.HOMEPAGE_CONTENT)
    public ResponseEntity<BaseResponse<String>> getHomepageContent(@RequestHeader String token) {
        Long userId = jwtManager.getUserIdFromToken(token);
        // opsiyonel: userId ile yetki kontrolü eklenebilir

        StaticContent content = staticContentService.getContentByKey("homepage");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Ana sayfa içeriği getirildi.")
                .data(content.getContent())
                .build());
    }

    @GetMapping(EndPoints.HOW_IT_WORKS)
    public ResponseEntity<BaseResponse<String>> getHowItWorks(@RequestHeader String token) {
        Long userId = jwtManager.getUserIdFromToken(token);

        StaticContent content = staticContentService.getContentByKey("how-it-works");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Nasıl çalışır içeriği getirildi.")
                .data(content.getContent())
                .build());
    }

    @GetMapping(EndPoints.PLATFORM_FEATURES)
    public ResponseEntity<BaseResponse<String>> getPlatformFeatures(@RequestHeader String token) {
        Long userId = jwtManager.getUserIdFromToken(token);

        StaticContent content = staticContentService.getContentByKey("platform-features");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Platform özellikleri getirildi.")
                .data(content.getContent())
                .build());
    }

    @GetMapping(EndPoints.HOLIDAYS)
    public ResponseEntity<BaseResponse<String>> getHolidays(@RequestHeader String token) {
        Long userId = jwtManager.getUserIdFromToken(token);

        StaticContent content = staticContentService.getContentByKey("holidays");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Tatiller getirildi.")
                .data(content.getContent())
                .build());
    }
}
