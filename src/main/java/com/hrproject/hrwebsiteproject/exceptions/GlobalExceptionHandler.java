package com.hrproject.hrwebsiteproject.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException e) {
        return createResponseEntity(ErrorType.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                List.of(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return createResponseEntity(ErrorType.JSON_CONVERT_ERROR,
                ErrorType.JSON_CONVERT_ERROR.getHttpStatus(),
                null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> fieldErrors = new ArrayList<>();
        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    fieldErrors.add(fieldError.getField()+" : "+fieldError.getDefaultMessage());
                });
        return createResponseEntity(ErrorType.VALIDATION_ERROR,HttpStatus.BAD_REQUEST,fieldErrors);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = e.getMessage();
        String extractedMessage = extractDuplicateKeyMessage(message);

        // Unique
        if (extractedMessage != null) {
            return createResponseEntity(ErrorType.DUPLICATE_KEY,
                    HttpStatus.BAD_REQUEST,
                    List.of(extractedMessage));
        }

        // Diğerleri
        return createResponseEntity(ErrorType.DATA_INTEGRITY_ERROR,
                HttpStatus.BAD_REQUEST,
                List.of(e.getMessage()));
    }

    /**
     * PostgreSQL unique constraint violation hata mesajından özel bilgiyi çıkarır
     * "Key (username)=(arda) already exists." gibi
     */
    private String extractDuplicateKeyMessage(String errorMessage) {
        if (errorMessage == null) return null;

        // PostgreSQL için regex paterni - tam olarak istenen bölümü çıkaralım
        Pattern pattern = Pattern.compile("Key \\(\\w+\\)=\\(.*?\\) already exists\\.");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

    public ResponseEntity<ErrorMessage> createResponseEntity(ErrorType errorType,HttpStatus httpStatus,
                                                             List<String> fieldErrors) {
        log.error("TÜM HATALARIN GEÇTİĞİ METOD:"+errorType.getCode()+" : "+errorType.getMessage()+" - "+fieldErrors);
        return new ResponseEntity<>(ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .success(false)
                .details(fieldErrors).build(),httpStatus);
    }
}
