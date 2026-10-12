package com.jobflow.aiservice.controller;

import com.jobflow.aiservice.dto.ResumeAnalysisRequest;
import com.jobflow.aiservice.dto.ResumeAnalysisResponse;
import com.jobflow.aiservice.service.ResumeAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/resume")
public class ResumeAnalysisController {

    private final ResumeAnalysisService resumeAnalysisService;

    public ResumeAnalysisController(
            ResumeAnalysisService resumeAnalysisService) {
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(
            @Valid @RequestBody ResumeAnalysisRequest request) {

        ResumeAnalysisResponse response =
                resumeAnalysisService.analyzeResume(request);

        return ResponseEntity.ok(response);
    }
}