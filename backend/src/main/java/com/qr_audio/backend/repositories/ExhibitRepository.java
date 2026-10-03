package com.qr_audio.backend.repositories;

import com.qr_audio.backend.entities.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {
}
