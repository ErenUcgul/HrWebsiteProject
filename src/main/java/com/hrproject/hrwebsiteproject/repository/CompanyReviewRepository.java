package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.CompanyReview;
import com.hrproject.hrwebsiteproject.model.enums.EReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
    Optional<CompanyReview> findByCompanyIdAndManagerUserId(Long companyId, Long managerUserId);
    List<CompanyReview> findAllByReviewStatus(EReviewStatus reviewStatus);


}
