package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.EMaterialStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_materials")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String model;
    private String serialNumber;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EMaterialStatus status;

    @Builder.Default
    private Boolean active = true;
}

