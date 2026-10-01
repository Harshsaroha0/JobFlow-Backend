package com.jobflow.resumeservice.controller;

import com.jobflow.resumeservice.entity.Resume;
import com.jobflow.resumeservice.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/user/{userId}/versions")
    public ResponseEntity<List<Resume>> getResumeVersions(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                resumeService.getResumesByUserId(userId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Resume>> getResumesByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                resumeService.getResumesByUserId(userId)
        );
    }

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(
            @RequestParam Long userId,
            @RequestParam("file") MultipartFile file) {

        Resume uploadedResume =
                resumeService.uploadResume(userId, file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(uploadedResume);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResumeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resumeService.getResumeById(id)
        );
    }


    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long id) {

        Resume resume = resumeService.getResumeById(id);

        Resource resource =
                resumeService.downloadResume(id);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"" +
                                resume.getFileName() + "\""
                )
                .header(
                        "Content-Type",
                        resume.getFileType()
                )
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable Long id) {

        resumeService.deleteResume(id);

        return ResponseEntity.noContent().build();
    }
}