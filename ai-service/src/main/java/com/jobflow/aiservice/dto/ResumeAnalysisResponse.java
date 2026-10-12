package com.jobflow.aiservice.dto;

import java.util.List;

public record ResumeAnalysisResponse(

        List<String> skills,
        List<String> experience,
        List<String> education,
        List<String> projects
) {
}