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
@Table(name = "tbl_leave_type")
public class LeaveType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;


    public LeaveType(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
