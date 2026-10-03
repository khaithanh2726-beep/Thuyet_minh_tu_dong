package com.qr_audio.backend.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exhibits")
@Getter
@Setter
@NoArgsConstructor
public class Exhibit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "default_image_url", length = 255)
    private String defaultImageUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "exhibit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExhibitTranslation> translations = new ArrayList<>();

    public void addTranslation(ExhibitTranslation translation) {
        translations.add(translation);
        translation.setExhibit(this);
    }

    public void removeTranslation(ExhibitTranslation translation) {
        translations.remove(translation);
        translation.setExhibit(null);
    }
}
