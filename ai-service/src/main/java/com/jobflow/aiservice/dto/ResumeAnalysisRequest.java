package com.jobflow.aiservice.dto;


import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

public record ResumeAnalysisRequest(
        @NotBlank(message = "Resume text is required")
        String resumeText) {

        @Value("${spring.ai.openai.api-key:}")
        public static String apiKey;
}