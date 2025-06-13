package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Optional<Company> findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> findByUserId(Long id) {
        return companyRepository.findByUserId(id);
    }



    public int countAll() {
        return (int) companyRepository.count();
    }

    public int countPending() {
        return Math.toIntExact(companyRepository.countByState((ECompanyState.PENDING)));
    }
    public Optional<Company> findByCompanyId(Long companyId) {
        return companyRepository.findById(companyId);
    }

}