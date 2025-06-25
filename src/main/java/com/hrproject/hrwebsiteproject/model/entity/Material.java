package com.hrproject.hrwebsiteproject.model.entity;

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

    private String name;         // Örn: Telefon, Laptop, Araba
    private String brand;        // Marka
    private String model;        // Model
    private String serialNumber; // Seri numarası
    private String description;  // Açıklama
    private Boolean active;
}

