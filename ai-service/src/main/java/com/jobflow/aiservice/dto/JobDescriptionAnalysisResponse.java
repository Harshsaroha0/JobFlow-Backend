package com.jobflow.aiservice.dto;

import java.util.List;

public record JobDescriptionAnalysisResponse(
        List<String> requiredSkills,
        List<String> responsibilities,
        List<String> qualifications
) {}