package com.jobflow.applicationtrackingservice.controller;

import com.jobflow.applicationtrackingservice.entity.Application;
import com.jobflow.applicationtrackingservice.entity.ApplicationStatus;
import com.jobflow.applicationtrackingservice.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Application> createApplication(
            @Valid @RequestBody Application application) {

        Application createdApplication =
                applicationService.createApplication(
                        application.getUserId(),
                        application.getJobId(),
                        application.getStatus(),
                        application.getNotes()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdApplication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                applicationService.getApplicationById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Application>> getApplicationsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "updatedAt")
        );

        return ResponseEntity.ok(
                applicationService.getApplicationsByUser(
                        userId,
                        pageable
                )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status,
            @RequestParam(required = false) String notes) {

        return ResponseEntity.ok(
                applicationService.updateStatus(
                        id,
                        status,
                        notes
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }
}