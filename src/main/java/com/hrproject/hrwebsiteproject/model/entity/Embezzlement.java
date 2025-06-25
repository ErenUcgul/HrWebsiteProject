package com.hrproject.hrwebsiteproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_embezzlement")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Embezzlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long materialId;

    private Long userId;

    private Long managerId;


    //Zimmetin ilk atandığı tarih.
    @Column(nullable = false)
    private LocalDateTime assignedAt;

    //Zimmetin iade edildiği tarih. Eğer null ise, henüz iade edilmemiştir.
    private LocalDateTime returnDate;

    //Zimmet iade edildi mi? (true = iade edildi)
    @Builder.Default
    private Boolean isReturned = false;

     //Zimmet kaydı aktif mi? (soft delete veya pasif durumu yönetmek için)
    @Builder.Default
    private Boolean active = true;
}
