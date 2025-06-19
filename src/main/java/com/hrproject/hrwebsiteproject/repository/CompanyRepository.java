package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByUserId(Long userId);

    int countByState(ECompanyState state);

    int countByCreateAtGreaterThan(Long timestamp);

    List<Company> findAllByState(ECompanyState state);

}
