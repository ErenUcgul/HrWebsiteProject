package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.entity.StaticContent;
import com.hrproject.hrwebsiteproject.repository.StaticContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaticContentService {
    private final StaticContentRepository staticContentRepository;

    public StaticContent getContentByKey(String key) {
        return staticContentRepository.findByKey(key)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.CONTENT_NOT_FOUND));
    }

    public StaticContent updateContent(String key, String newContent) {
        StaticContent content = staticContentRepository.findByKey(key)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.CONTENT_NOT_FOUND));
        content.setContent(newContent);
        return staticContentRepository.save(content);
    }
}
