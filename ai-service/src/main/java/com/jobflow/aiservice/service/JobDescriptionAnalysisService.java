
package com.jobflow.aiservice.service;

import com.jobflow.aiservice.dto.JobDescriptionAnalysisRequest;
import com.jobflow.aiservice.dto.JobDescriptionAnalysisResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class JobDescriptionAnalysisService {

    private static final List<String> KNOWN_SKILLS = List.of(
            "Java", "Spring Boot", "Spring Security", "Spring AI",
            "Spring", "Hibernate", "JPA", "JDBC", "REST APIs",
            "Microservices", "MySQL", "PostgreSQL", "MongoDB",
            "SQL", "DSA", "Data Structures", "HTML", "CSS",
            "JavaScript", "Git", "Docker", "Kafka", "Redis",
            "JUnit", "Maven"
    );

    public JobDescriptionAnalysisResponse analyzeJobDescription(
            JobDescriptionAnalysisRequest request) {

        String description = request.jobDescription();

        List<String> requiredSkills = extractSkills(description);
        List<String> responsibilities = extractSection(
                description,
                "responsibilities",
                "what you'll do",
                "what you will do",
                "key duties",
                "roles and responsibilities"
        );
        List<String> qualifications = extractSection(
                description,
                "qualifications",
                "requirements",
                "what we're looking for",
                "what we are looking for",
                "minimum qualifications",
                "preferred qualifications"
        );

        return new JobDescriptionAnalysisResponse(
                requiredSkills,
                responsibilities,
                qualifications
        );
    }

    private List<String> extractSkills(String description) {
        String normalized = description.toLowerCase(Locale.ROOT);
        List<String> skills = new ArrayList<>();

        for (String skill : KNOWN_SKILLS) {
            Pattern pattern = Pattern.compile(
                    "(?<![a-z0-9])"
                            + Pattern.quote(skill.toLowerCase(Locale.ROOT))
                            + "(?![a-z0-9])"
            );

            if (pattern.matcher(normalized).find()) {
                skills.add(skill);
            }
        }

        return skills;
    }

    private List<String> extractSection(
            String description, String... headings) {

        String[] lines = description.split("\\R");
        List<String> result = new ArrayList<>();
        boolean collecting = false;

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.isEmpty()) {
                continue;
            }

            String normalized = trimmed
                    .replaceAll("^[\\s#*\\-]+|[:*]+$", "")
                    .trim()
                    .toLowerCase(Locale.ROOT);

            boolean headingFound = false;

            for (String heading : headings) {
                if (normalized.equals(heading)) {
                    collecting = true;
                    headingFound = true;
                    break;
                }
            }

            if (headingFound) {
                continue;
            }

            if (collecting && isSectionHeading(normalized)) {
                break;
            }

            if (collecting) {
                result.add(trimmed);
            }
        }

        return result;
    }

    private boolean isSectionHeading(String line) {
        return switch (line.replaceAll("^[\\s#*\\-]+|[:*]+$", "").trim()) {
            case "responsibilities", "what you'll do",
                 "what you will do", "key duties",
                 "roles and responsibilities", "qualifications",
                 "requirements", "what we're looking for",
                 "what we are looking for", "minimum qualifications",
                 "preferred qualifications", "skills",
                 "technical skills", "benefits", "about us",
                 "about the role", "job summary", "education",
                 "experience" -> true;
            default -> false;
        };
    }
}
