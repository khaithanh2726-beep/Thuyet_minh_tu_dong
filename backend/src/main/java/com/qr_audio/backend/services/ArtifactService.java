package com.qr_audio.backend.services;

import com.qr_audio.backend.dto.ArtifactRequest;
import com.qr_audio.backend.dto.ArtifactResponse;
import com.qr_audio.backend.entities.Exhibit;
import com.qr_audio.backend.entities.ExhibitTranslation;
import com.qr_audio.backend.exceptions.ResourceNotFoundException;
import com.qr_audio.backend.repositories.ExhibitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ArtifactService {

    private static final String DEFAULT_LANGUAGE = "vi";

    private final ExhibitRepository exhibitRepository;

    public ArtifactService(ExhibitRepository exhibitRepository) {
        this.exhibitRepository = exhibitRepository;
    }

    @Transactional(readOnly = true)
    public List<ArtifactResponse> getAllArtifacts() {
        return exhibitRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArtifactResponse getArtifactById(Long id) {
        return toResponse(findExhibit(id));
    }

    public ArtifactResponse createArtifact(ArtifactRequest request) {
        Exhibit exhibit = new Exhibit();
        String languageCode = applyRequest(exhibit, request);
        return toResponse(exhibitRepository.save(exhibit), languageCode);
    }

    public ArtifactResponse updateArtifact(Long id, ArtifactRequest request) {
        Exhibit exhibit = findExhibit(id);
        String languageCode = applyRequest(exhibit, request);
        return toResponse(exhibitRepository.save(exhibit), languageCode);
    }

    public void deleteArtifact(Long id) {
        Exhibit exhibit = findExhibit(id);
        exhibitRepository.delete(exhibit);
    }

    private Exhibit findExhibit(Long id) {
        return exhibitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hiện vật với ID: " + id));
    }

    private String applyRequest(Exhibit exhibit, ArtifactRequest request) {
        String languageCode = request.languageCode() == null || request.languageCode().isBlank()
                ? DEFAULT_LANGUAGE
                : request.languageCode().trim().toLowerCase();

        exhibit.setDefaultImageUrl(request.imageUrl());

        ExhibitTranslation translation = exhibit.getTranslations().stream()
                .filter(item -> item.getLanguageCode().equalsIgnoreCase(languageCode))
                .findFirst()
                .orElseGet(() -> {
                    ExhibitTranslation created = new ExhibitTranslation();
                    created.setLanguageCode(languageCode);
                    exhibit.addTranslation(created);
                    return created;
                });
        translation.setTitle(request.name().trim());
        translation.setDescription(request.description());
        return languageCode;
    }

    private ArtifactResponse toResponse(Exhibit exhibit) {
        ExhibitTranslation translation = exhibit.getTranslations().stream()
                .min(Comparator.comparing(item -> !DEFAULT_LANGUAGE.equalsIgnoreCase(item.getLanguageCode())))
                .orElse(null);
        return toResponse(exhibit, translation == null ? null : translation.getLanguageCode());
    }

    private ArtifactResponse toResponse(Exhibit exhibit, String preferredLanguage) {
        ExhibitTranslation translation = exhibit.getTranslations().stream()
                .filter(item -> preferredLanguage != null && item.getLanguageCode().equalsIgnoreCase(preferredLanguage))
                .findFirst()
                .orElseGet(() -> exhibit.getTranslations().stream()
                        .min(Comparator.comparing(item -> !DEFAULT_LANGUAGE.equalsIgnoreCase(item.getLanguageCode())))
                        .orElse(null));

        return new ArtifactResponse(
                exhibit.getId(),
                translation == null ? null : translation.getTitle(),
                translation == null ? null : translation.getDescription(),
                exhibit.getDefaultImageUrl(),
                translation == null ? null : translation.getLanguageCode(),
                exhibit.getCreatedAt(),
                exhibit.getUpdatedAt()
        );
    }
}
