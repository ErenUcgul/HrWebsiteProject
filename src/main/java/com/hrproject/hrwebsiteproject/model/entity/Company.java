package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyType;
import com.hrproject.hrwebsiteproject.model.enums.ERegion;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_company")
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(nullable = false, unique = true, length = 100)
    private String companyName;
    @Column(nullable = false, length = 100)
    private String companyAddress;
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ERegion region;
//    @Column(nullable = false, length = 15)
    private String companyPhone;
//    @Column(nullable = false, unique = true, length = 50)
    private String companyEmail;
    private String companyLogo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ECompanyType companyType;
    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private ECompanyState state;

}
