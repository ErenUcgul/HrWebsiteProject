package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.EEmbezzlementDuration;
import com.hrproject.hrwebsiteproject.model.enums.EEmbezzlementType;
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

    private LocalDateTime assignedAt;

    private LocalDateTime returnDate;

    @Builder.Default
    private Boolean isReturned = false;

    @Builder.Default
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    private EEmbezzlementDuration duration;

    @Enumerated(EnumType.STRING)
    private EEmbezzlementType type;
}
