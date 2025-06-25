package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.request.MaterialRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.MaterialResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Material;
import com.hrproject.hrwebsiteproject.repository.MaterialMapper;
import com.hrproject.hrwebsiteproject.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    public void createMaterial(MaterialRequestDto dto) {
        if (materialRepository.existsBySerialNumber(dto.serialNumber())) {
            throw new HrWebsiteProjectException(ErrorType.MATERIAL_ALREADY_EXISTS);
        }

        Material material = materialMapper.toEntity(dto);
        material.setActive(true);
        materialRepository.save(material);
    }

    public List<MaterialResponseDto> getAllActiveMaterials() {
        return materialRepository.findAllByActiveTrue().stream()
                .map(materialMapper::toResponseDto)
                .toList();
    }

    public Material findById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.NOTFOUND_MATERIAL));
    }
}
