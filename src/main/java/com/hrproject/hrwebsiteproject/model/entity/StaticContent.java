package com.hrproject.hrwebsiteproject.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_static_content")
public class StaticContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    private String key; // "homepage", "how-it-works", "platform-features", vs.

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String title; // Opsiyonel başlık (örn: "Nasıl Çalışır?")
}
