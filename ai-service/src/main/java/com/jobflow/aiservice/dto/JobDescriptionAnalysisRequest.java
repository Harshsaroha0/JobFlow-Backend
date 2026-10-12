
package com.jobflow.aiservice.dto;

import jakarta.validation.constraints.NotBlank;

public record JobDescriptionAnalysisRequest(
        @NotBlank(message = "Job description is required")
        String jobDescription
) {}
