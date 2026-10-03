package com.qr_audio.backend.controllers;

import com.qr_audio.backend.dto.ArtifactRequest;
import com.qr_audio.backend.dto.ArtifactResponse;
import com.qr_audio.backend.services.ArtifactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @GetMapping
    public List<ArtifactResponse> getAllArtifacts() {
        return artifactService.getAllArtifacts();
    }

    @GetMapping("/{id}")
    public ArtifactResponse getArtifactById(@PathVariable Long id) {
        return artifactService.getArtifactById(id);
    }

    @PostMapping
    public ResponseEntity<ArtifactResponse> createArtifact(@Valid @RequestBody ArtifactRequest request) {
        ArtifactResponse created = artifactService.createArtifact(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ArtifactResponse updateArtifact(@PathVariable Long id,
                                           @Valid @RequestBody ArtifactRequest request) {
        return artifactService.updateArtifact(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtifact(@PathVariable Long id) {
        artifactService.deleteArtifact(id);
        return ResponseEntity.noContent().build();
    }
}
