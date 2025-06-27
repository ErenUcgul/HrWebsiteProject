package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.EmbezzlementMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.AddEmbezzlementRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.AssigmentEmbezzlementRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.DeleteEmbezzlementRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmbezzlementResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.PersonalEmbezzlementResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Embezzlement;
import com.hrproject.hrwebsiteproject.repository.EmbezzlementRepository;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmbezzlementService {

    private final EmbezzlementRepository embezzlementRepository;
    private final EmbezzlementMapper embezzlementMapper;
    private final JwtManager jwtManager;
    private final MaterialService materialService;
    private final CompanyService companyService;
    private final UserService userService;

    public void addEmbezzlement(AddEmbezzlementRequestDto dto) {
        Long managerId = jwtManager.getUserIdFromToken(dto.token());

        // Material var mı kontrolü
        materialService.validateMaterialExists(dto.materialId());

        // Material zaten aktif zimmetlenmiş mi?
        boolean alreadyAssigned = embezzlementRepository
                .existsByMaterialIdAndIsReturnedFalseAndActiveTrue(dto.materialId());

        if (alreadyAssigned) {
            throw new HrWebsiteProjectException(ErrorType.MATERIAL_ALREADY_ADDED);
        }

        Embezzlement embezzlement = Embezzlement.builder()
                .materialId(dto.materialId())
                .managerId(managerId)
                .assignedAt(LocalDateTime.now())
                .active(true)
                .build();

        embezzlementRepository.save(embezzlement);
    }

    public List<EmbezzlementResponseDto> getActiveEmbezzlementList(String token) {
        Long managerId = jwtManager.getUserIdFromToken(token);

        List<Embezzlement> list = embezzlementRepository.findAllByManagerIdAndActiveTrue(managerId);
        return list.stream()
                .map(embezzlementMapper::toResponseDto)
                .toList();
    }

    public List<EmbezzlementResponseDto> getPassiveEmbezzlementList(String token) {
        Long managerId = jwtManager.getUserIdFromToken(token);

        List<Embezzlement> list = embezzlementRepository.findAllByManagerIdAndActiveFalse(managerId);
        return list.stream()
                .map(embezzlementMapper::toResponseDto)
                .toList();
    }

    public void assignEmbezzlement(AssigmentEmbezzlementRequestDto dto) {
        Long managerId = jwtManager.getUserIdFromToken(dto.token());

        Embezzlement embezzlement = embezzlementRepository.findById(dto.embezzlementId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.NOTFOUND_EMBEZZLEMENT));

        // Eğer zaten atanmışsa (userId doluysa), tekrar atama yapma
        if (embezzlement.getUserId() != null) {
            throw new HrWebsiteProjectException(ErrorType.EMBEZZLEMENT_ALREADY_ASSIGNED);
        }

        embezzlement.setUserId(dto.userId());
        embezzlementRepository.save(embezzlement);
    }

    public List<PersonalEmbezzlementResponseDto> getMyEmbezzlementList(String token) {
        Long userId = jwtManager.getUserIdFromToken(token);

        List<Embezzlement> list = embezzlementRepository.findAllByUserIdAndActiveTrue(userId);
        return list.stream()
                .map(embezzlementMapper::toPersonalResponseDto)
                .toList();
    }

    public void deleteEmbezzlementByUser(DeleteEmbezzlementRequestDto dto) {
        Long managerId = jwtManager.getUserIdFromToken(dto.token());

        List<Embezzlement> embezzlements = embezzlementRepository.findAllByUserIdAndActiveTrue(dto.userId());

        if (embezzlements.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.NOTFOUND_EMBEZZLEMENT);
        }

        embezzlements.forEach(e -> e.setActive(false));
        embezzlementRepository.saveAll(embezzlements);
    }

    public void deleteEmbezzlementById(Long embezzlementId, String token) {
        Long managerId = jwtManager.getUserIdFromToken(token);

        Embezzlement embezzlement = embezzlementRepository.findByIdAndActiveTrue(embezzlementId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.NOTFOUND_EMBEZZLEMENT));

        embezzlement.setActive(false);
        embezzlementRepository.save(embezzlement);
    }


    public void returnEmbezzlement(Long embezzlementId, Long userId) {
        Embezzlement embezzlement = embezzlementRepository.findById(embezzlementId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.NOTFOUND_EMBEZZLEMENT));

        if (!embezzlement.getUserId().equals(userId)) {
            throw new HrWebsiteProjectException(ErrorType.UNAUTHORIZED_ACCESS);
        }

        if (Boolean.TRUE.equals(embezzlement.getIsReturned())) {
            throw new HrWebsiteProjectException(ErrorType.EMBEZZLEMENT_ALREADY_RETURNED);
        }

        embezzlement.setIsReturned(true);               // Zimmet iade edildi
//        embezzlement.setActive(false);                  // Artık aktif değil
        embezzlement.setReturnDate(LocalDateTime.now());
        embezzlement.setUserId(null);

        embezzlementRepository.save(embezzlement);
    }
//    public List<EmbezzlementResponseDto> getAllEmbezzlementsByCompany(String token) {
//        Long managerId = jwtManager.getUserIdFromToken(token);
//        Long companyId = companyService.getCompanyIdByUserId(managerId);
//
//        List<Embezzlement> embezzlements = embezzlementRepository.findAllByUserIdIn(Collections.singletonList(companyId));
//
//        return embezzlements.stream()
//                .map(embezzlementMapper::toResponseDto)
//                .collect(Collectors.toList());
//    }

    public List<EmbezzlementResponseDto> getAllEmbezzlementListByManager(String token) {
        Long managerId = jwtManager.getUserIdFromToken(token);

        List<Embezzlement> list = embezzlementRepository.findAllByManagerId(managerId);
        return list.stream()
                .map(embezzlementMapper::toResponseDto)
                .toList();
    }
}

