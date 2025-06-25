package com.hrproject.hrwebsiteproject.model.entity;

import com.hrproject.hrwebsiteproject.model.enums.ESalaryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.YearMonth;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_salary_payment")
public class SalaryPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;
    private Long companyId;

    private YearMonth paymentMonth;

    private Double baseSalary;
    private Double approvedExpenseTotal;
    private Double totalPayable;

    @Enumerated(EnumType.STRING)
    private ESalaryStatus status; // PENDING, PAID

}
