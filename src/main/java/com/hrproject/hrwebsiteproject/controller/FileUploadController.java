package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(EndPoints.FILE)
@RequiredArgsConstructor
@CrossOrigin("*")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Operation(
            summary = "Dosya yükle",
            description = "Yalnızca .pdf, .jpeg veya .jpg uzantılı dosyalar kabul edilir."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dosya başarıyla yüklendi"),
            @ApiResponse(responseCode = "400", description = "Geçersiz dosya türü veya yükleme hatası"),
    })
    @PostMapping(value = EndPoints.UPLOAD_PDF_JPG, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<String>> uploadFile(
            @RequestParam("file") MultipartFile file) {

        String uploadedFileName = fileUploadService.uploadFile(file);

        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .success(true)
                .message("Dosya başarıyla yüklendi.")
                .data(uploadedFileName)
                .build());
    }

    @Operation(summary = "Yüklenen dosyaları listele", description = "Sunucudaki tüm yüklenen dosyaların listesini döner.")
    @GetMapping(EndPoints.LIST_FILES)
    public ResponseEntity<BaseResponse<List<String>>> listUploadedFiles() {
        List<String> files = fileUploadService.listFiles();
        return ResponseEntity.ok(BaseResponse.<List<String>>builder()
                .code(200)
                .success(true)
                .message("Dosyalar başarıyla listelendi.")
                .data(files)
                .build());
    }

    @Operation(summary = "Dosya indir", description = "Belirtilen dosya sunucudan indirilir.")
    @GetMapping(EndPoints.DOWNLOAD_FILE)
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) throws IOException {
        Resource resource = fileUploadService.loadFileAsResource(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "Dosya sil", description = "Belirtilen dosya sunucudan silinir.")
    @DeleteMapping(EndPoints.DELETE_FILE)
    public ResponseEntity<BaseResponse<Boolean>> deleteFile(@RequestParam String filename) {
        boolean result = fileUploadService.deleteFile(filename);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(result)
                .message(result ? "Dosya başarıyla silindi." : "Dosya silinemedi.")
                .data(result)
                .build());
    }
}