package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.model.enums.Egender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
//    @Column(nullable = false, length = 20)
    private String password;
//    @Column(nullable = false, length = 15)
    private String phone;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Egender gender;
    @Enumerated(EnumType.STRING)
    private EUserState state;


    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private EUserRole userRole;
    private String activationCode;
    private String passwordResetCode;
    //
    private LocalDateTime passwordResetCodeExpireDate;
    private Integer resetAttemptCount;

    private String pendingEmail; // Yeni email doğrulama bekliyor
    private String emailChangeCode; // Email değişiklik kodu

}
