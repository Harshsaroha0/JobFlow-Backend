package com.jobflow.aiservice.service;

import com.jobflow.aiservice.dto.ResumeAnalysisRequest;
import com.jobflow.aiservice.dto.ResumeAnalysisResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeAnalysisService {

    private static final List<String> KNOWN_SKILLS = List.of(
            "Java", "Spring Boot", "Spring", "Spring Security",
            "Spring AI", "Hibernate", "JPA", "JDBC",
            "REST APIs", "Microservices", "MySQL", "PostgreSQL",
            "MongoDB", "SQL", "DSA", "Data Structures",
            "HTML", "CSS", "JavaScript", "Git", "Docker",
            "Kafka", "Redis", "JUnit", "Maven"
    );

    public ResumeAnalysisResponse analyzeResume(
            ResumeAnalysisRequest request) {

        String resume = request.resumeText();

        List<String> skills = extractSkills(resume);
        List<String> experience = extractSection(
                resume, "experience", "work experience",
                "professional experience", "internships");
        List<String> education = extractSection(
                resume, "education", "academic background",
                "qualifications");
        List<String> projects = extractSection(
                resume, "projects", "personal projects",
                "academic projects");

        return new ResumeAnalysisResponse(
                skills, experience, education, projects);
    }

    private List<String> extractSkills(String resume) {
        String normalized = resume.toLowerCase(Locale.ROOT);
        List<String> skills = new ArrayList<>();

        for (String skill : KNOWN_SKILLS) {
            Pattern pattern = Pattern.compile(
                    "(?<![a-z0-9])"
                            + Pattern.quote(skill.toLowerCase(Locale.ROOT))
                            + "(?![a-z0-9])");

            if (pattern.matcher(normalized).find()) {
                skills.add(skill);
            }
        }

        return skills;
    }

    private List<String> extractSection(
            String resume, String... headings) {

        String[] lines = resume.split("\\R");
        List<String> result = new ArrayList<>();
        boolean collecting = false;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            String normalized = trimmed
                    .replaceAll("[:#*]", "")
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
        return switch (line.replaceAll("[:#*]", "").trim()) {
            case "skills", "technical skills", "experience",
                 "work experience", "professional experience",
                 "internships", "education", "academic background",
                 "projects", "personal projects", "academic projects",
                 "certifications", "achievements", "summary",
                 "profile", "objective" -> true;
            default -> false;
        };
    }
}
