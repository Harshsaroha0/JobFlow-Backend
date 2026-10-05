package com.jobflow.applicationtrackingservice.service;

import com.jobflow.applicationtrackingservice.entity.Application;
import com.jobflow.applicationtrackingservice.entity.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {

    Application createApplication(
            Long userId,
            Long jobId,
            ApplicationStatus status,
            String notes
    );

    Application getApplicationById(Long id);

    Page<Application> getApplicationsByUser(
            Long userId,
            Pageable pageable
    );

    Application updateStatus(
            Long id,
            ApplicationStatus status,
            String notes
    );

    void deleteApplication(Long id);
}