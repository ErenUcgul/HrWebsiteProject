package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyStateInfoResponse;
import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public Optional<Company> findByCompanyId(Long companyId) {
        return companyRepository.findById(companyId);
    }

    public int countByState(ECompanyState eCompanyState) {
        return companyRepository.countByState(eCompanyState);
    }

    public int countCompaniesRegisteredLast7Days() {
        long sevenDaysAgo = System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000);
        return companyRepository.countByCreateAtGreaterThan(sevenDaysAgo);
    }

    public List<CompanyStateInfoResponse> getAllCompaniesWithStateInfo() {
        return companyRepository.findAll().stream()
                .map(c -> CompanyStateInfoResponse.builder()
                        .companyName(c.getCompanyName())
                        .state(c.getState())
                        .build())
                .toList();
    }

    public List<CompanyStateInfoResponse> getCompaniesByState(ECompanyState state) {
        return companyRepository.findAllByState(state).stream()
                .map(c -> new CompanyStateInfoResponse(c.getCompanyName(), c.getState()))
                .toList();
    }

    public Long getCompanyIdByUserId(Long userId) {
        return companyRepository.findByUserId(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND))
                .getId();
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }
}