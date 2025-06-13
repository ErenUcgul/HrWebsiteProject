package com.hrproject.hrwebsiteproject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_SERVER_ERROR(500, "SUNUCUDA BEKLENMEYEN HATA.", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(400, "Girilen parametreler hatalıdır. Kontrol ediniz.", HttpStatus.BAD_REQUEST),
    JSON_CONVERT_ERROR(300, "Girilen parametreler hatalıdır. Json Dönüşüm Hatası.", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_ERROR(4000, "Parametre hatası", HttpStatus.BAD_REQUEST),
    DUPLICATE_KEY(3000, "Benzersiz alan hatası.", HttpStatus.BAD_REQUEST),
    DATA_INTEGRITY_ERROR(3001, "Veri bütünlüğü hatası", HttpStatus.BAD_REQUEST),


    //REGISTER
    INVALID_USERNAME_OR_PASSWORD(4001, "Username veya Password Hatalı.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4002, "Geçersiz token bilgisi.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4003, "User bulunamadı", HttpStatus.NOT_FOUND),
    PASSWORD_MISMATCH_ERROR(4004, "Passwordler uyuşmuyor.", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_TAKEN(4005, "Mail adresiniz zaten kayıtlı.", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST_COMPANY(4006, "Şirket zaten kayıtlı.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_IN_REVIEW(4007, "Kullanıcı zaten inceleme altında.", HttpStatus.BAD_REQUEST),
    INVALID_ACTIVATION_CODE(4008, "Aktivasyon kodu hatalı", HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_EXPIRED(4009,"Aktivasyon kodunun süresi doldu" ,HttpStatus.BAD_REQUEST ),
    COMPANY_NOT_FOUND(4010,"Şirket bulunamadı" ,HttpStatus.BAD_REQUEST),
    COMPANY_ALREADY_ACTIVE(4011,"Şirket zaten aktif!" , HttpStatus.BAD_REQUEST ),
    USER_ALREADY_ACTIVE(4012,"Kullanıcı zaten aktif!" , HttpStatus.BAD_REQUEST ),
    USER_NOT_ACTIVE(4013,"Hesabınız aktif değil" ,HttpStatus.BAD_REQUEST ),
    COMPANY_NOT_ACTIVE(4014,"Şirketiniz aktif değil" ,HttpStatus.BAD_REQUEST ),
    RESET_CODE_ALREADY_SENT(4015,"Şifre sıfırlama kodu zaten gönderilmiş" ,HttpStatus.BAD_REQUEST ),
    INVALID_RESET_CODE(4016,"Şifre sıfırlama kodunu hatalı girdiniz" ,HttpStatus.BAD_REQUEST ),
    PASSWORD_SAME_AS_OLD(4017,"Yeni şifreniz eski şifrenizle aynı olamaz" ,HttpStatus.BAD_REQUEST ),
    INVALID_COMPANY_EMAIL_DOMAIN(4018,"Şirket emaili ile kayıt olunuz" ,HttpStatus.BAD_REQUEST ),
    EMAIL_SAME_AS_OLD(4019,"Yeni mailiniz eski mailinizle aynı!" ,HttpStatus.BAD_REQUEST ),
    NO_PENDING_EMAIL_CHANGE(4020,"Yeni email adresiniz bulunamadı" ,HttpStatus.BAD_REQUEST ),
    INVALID_OLD_PASSWORD(4021,"Eski şifrenizi hatalı girdiniz" ,HttpStatus.BAD_REQUEST ),
    CANNOT_DEACTIVATE_SUPER_ADMIN(4022,"Süper Admin (İlk Admin) deaktif edilemez" , HttpStatus.BAD_REQUEST ),
    CONTENT_NOT_FOUND(4023,"İçerik bulunamadı" ,HttpStatus.BAD_REQUEST ),
    INVALID_REFRESH_TOKEN(4024,"Refresh token geçersiz" ,HttpStatus.BAD_REQUEST ),
    EXPIRED_REFRESH_TOKEN(4025,"Refresh tokenın süresi doldu " ,HttpStatus.BAD_REQUEST ),
    COMPANY_ALREADY_REJECTED(4026,"Şirket zaten reddedilmiş" ,HttpStatus.BAD_REQUEST ),
    COMPANY_ALREADY_APPROVED(4027,"Şirket zaten onaylanmış" ,HttpStatus.BAD_REQUEST ),
    USER_STATE_IS_PENDING(4028,"Kullanıcı PENDING durumunda." ,HttpStatus.BAD_REQUEST ),
    COMPANY_STATE_IS_PENDING(4029,"Şirket PENDING durumunda." ,HttpStatus.BAD_REQUEST ),;


    int code;
    String message;
    HttpStatus httpStatus;

}
