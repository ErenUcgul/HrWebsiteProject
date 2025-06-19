package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyType;
import com.hrproject.hrwebsiteproject.model.enums.ERegion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_company_leave_types")
public class CompanyLeaveType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private Long leaveTypeId; // LeaveType ID üzerinden ilişki kurulacak

    @Column(nullable = false)
    private Integer defaultDayCount; // Şirketin verdiği izin süresi (gün cinsinden)

    @Column(nullable = false)
    private Boolean requiresApproval; // Onay gerekiyor mu

    @Column(nullable = false)
    private Boolean active; // Şu an kullanımda mı

}
