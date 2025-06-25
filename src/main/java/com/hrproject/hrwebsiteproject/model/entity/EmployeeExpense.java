package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyType;
import com.hrproject.hrwebsiteproject.model.enums.EExpenseStatus;
import com.hrproject.hrwebsiteproject.model.enums.ERegion;
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
@Table(name = "tbl_employee_expense")
public class EmployeeExpense extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private Long companyId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EExpenseStatus status; // PENDING, APPROVED, REJECTED

    private String rejectionReason;
//    private boolean approved;
    private LocalDate expenseDate;

}
