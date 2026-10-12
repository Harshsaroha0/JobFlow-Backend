
package com.jobflow.aiservice.controller;

import com.jobflow.aiservice.dto.JobDescriptionAnalysisRequest;
import com.jobflow.aiservice.dto.JobDescriptionAnalysisResponse;
import com.jobflow.aiservice.service.JobDescriptionAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/job-description")
public class JobDescriptionAnalysisController {

    private final JobDescriptionAnalysisService analysisService;

    public JobDescriptionAnalysisController(
            JobDescriptionAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<JobDescriptionAnalysisResponse> analyzeJobDescription(
            @Valid @RequestBody JobDescriptionAnalysisRequest request) {

        JobDescriptionAnalysisResponse response =
                analysisService.analyzeJobDescription(request);

        return ResponseEntity.ok(response);
    }
}
