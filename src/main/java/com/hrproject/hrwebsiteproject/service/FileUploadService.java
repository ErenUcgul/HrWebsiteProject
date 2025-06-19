package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final Path rootLocation = Paths.get("uploads");

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.FILE_IS_EMPTY);
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename()).toLowerCase();
        String contentType = file.getContentType();

        //  Güvenlik kontrolü – uzantı ve MIME türü birlikte kontrol edilir
        boolean isPdf = filename.endsWith(".pdf") && "application/pdf".equals(contentType);
        boolean isJpeg = (filename.endsWith(".jpeg") || filename.endsWith(".jpg")) &&
                ("image/jpeg".equals(contentType) || "image/jpg".equals(contentType));

        if (!(isPdf || isJpeg)) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_FILE_TYPE);
        }

        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            // Dosya ismini benzersiz hale getir (aynı isimli yüklemeleri önler)
            String uniqueName = UUID.randomUUID().toString() + "_" + filename;
            Path destinationFile = rootLocation.resolve(uniqueName).normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return uniqueName;

        } catch (IOException e) {
            throw new HrWebsiteProjectException(ErrorType.FILE_UPLOAD_FAILED);
        }
    }

    public List<String> listFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(uploadDir), 1)) {
            return paths
                    .filter(path -> !path.equals(Paths.get(uploadDir)))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new HrWebsiteProjectException(ErrorType.FILE_OPERATION_FAILED);
        }
    }

    public Resource loadFileAsResource(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new HrWebsiteProjectException(ErrorType.FILE_NOT_FOUND);
        }
    }

    public boolean deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}
