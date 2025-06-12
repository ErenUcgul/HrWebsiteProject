package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.entity.StaticContent;
import com.hrproject.hrwebsiteproject.service.StaticContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoints.PUBLIC_API)
@RequiredArgsConstructor
@CrossOrigin("*")
public class StaticContentController {
    private final StaticContentService staticContentService;

    @GetMapping(EndPoints.HOMEPAGE_CONTENT)
    public ResponseEntity<BaseResponse<String>> getHomepageContent() {
        StaticContent content = staticContentService.getContentByKey("homepage");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Ana sayfa içeriği getirildi.")
                .data(content.getContent())
                .build());
    }

    @GetMapping(EndPoints.HOW_IT_WORKS)
    public ResponseEntity<BaseResponse<String>> getHowItWorks() {
        StaticContent content = staticContentService.getContentByKey("how-it-works");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Nasıl çalışır içeriği getirildi.")
                .data(content.getContent())
                .build());
    }

    @GetMapping(EndPoints.PLATFORM_FEATURES)
    public ResponseEntity<BaseResponse<String>> getPlatformFeatures() {
        StaticContent content = staticContentService.getContentByKey("platform-features");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Platform özellikleri getirildi.")
                .data(content.getContent())
                .build());
    }

    @GetMapping(EndPoints.HOLIDAYS)
    public ResponseEntity<BaseResponse<String>> getHolidays() {
        StaticContent content = staticContentService.getContentByKey("holidays");
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Tatiller getirildi.")
                .data(content.getContent())
                .build());
    }
}
