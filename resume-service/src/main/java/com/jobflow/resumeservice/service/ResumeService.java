package com.jobflow.resumeservice.service;

import com.jobflow.resumeservice.entity.Resume;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    Resume uploadResume(Long userId, MultipartFile file);

    List<Resume> getResumesByUserId(Long userId);

    Resume getResumeById(Long id);

    Resource downloadResume(Long id);

    void deleteResume(Long id);
}