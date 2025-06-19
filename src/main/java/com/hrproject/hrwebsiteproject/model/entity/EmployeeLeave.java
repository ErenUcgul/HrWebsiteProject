package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.ELeaveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_employee_leave")
public class EmployeeLeave extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private Long companyLeaveTypeId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int totalDays;

    @Enumerated(EnumType.STRING)
    private ELeaveStatus leaveStatus = ELeaveStatus.PENDING;

    private String reason;

}
