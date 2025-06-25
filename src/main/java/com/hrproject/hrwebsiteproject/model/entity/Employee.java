package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_employee")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long userId;
    @Column(nullable = false, unique = true, length = 11)
    private String identityNo;
    @Column(nullable = false)
    @Past
    private LocalDate birthDate;
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPosition position;
    @Column(nullable = false)
    private LocalDate dateOfEmployment;
    private LocalDate dateOfTermination;
    @Column(nullable = false)
    private Double salary;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EEmploymentStatus employmentStatus;
    @Column(nullable = false, unique = true)
    private String socialSecurityNumber;
    private Long companyId;
}
