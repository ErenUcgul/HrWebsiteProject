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
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbezzlementService {

    private final EmbezzlementRepository embezzlementRepository;
    private final EmbezzlementMapper embezzlementMapper;
    private final JwtManager jwtManager;

    public void addEmbezzlement(AddEmbezzlementRequestDto dto) {
        Long managerId = jwtManager.getUserIdFromToken(dto.token());

        Embezzlement embezzlement = Embezzlement.builder()
                .materialId(dto.materialId())
                .managerId(managerId)
                .assignedAt(LocalDateTime.now())
                .active(true)
                .build();

        embezzlementRepository.save(embezzlement);
    }

    public List<EmbezzlementResponseDto> getEmbezzlementList(String token) {
        Long managerId = jwtManager.getUserIdFromToken(token);

        List<Embezzlement> list = embezzlementRepository.findAllByManagerIdAndActiveTrue(managerId);
        return list.stream()
                .map(embezzlementMapper::toResponseDto)
                .toList();
    }

    public void assignEmbezzlement(AssigmentEmbezzlementRequestDto dto) {
        Long managerId = jwtManager.getUserIdFromToken(dto.token());

        Embezzlement embezzlement = embezzlementRepository.findById(dto.embezzlementId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.NOTFOUND_EMBEZZLEMENT));

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

    public void returnEmbezzlement(Long embezzlementId, Long userId) {
        Embezzlement embezzlement = embezzlementRepository.findById(embezzlementId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.NOTFOUND_EMBEZZLEMENT));

        if (!embezzlement.getUserId().equals(userId)) {
            throw new HrWebsiteProjectException(ErrorType.UNAUTHORIZED_ACCESS);
        }

        if (Boolean.TRUE.equals(embezzlement.getIsReturned())) {
            throw new HrWebsiteProjectException(ErrorType.EMBEZZLEMENT_ALREADY_RETURNED);
        }

        embezzlement.setIsReturned(true);
        embezzlement.setReturnDate(LocalDateTime.now());
        embezzlementRepository.save(embezzlement);
    }

}
