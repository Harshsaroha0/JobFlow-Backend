package com.jobflow.applicationtrackingservice.serviceImpl;

import com.jobflow.applicationtrackingservice.entity.Application;
import com.jobflow.applicationtrackingservice.entity.ApplicationStatus;
import com.jobflow.applicationtrackingservice.exception.ApplicationAlreadyExistsException;
import com.jobflow.applicationtrackingservice.exception.ApplicationNotFoundException;
import com.jobflow.applicationtrackingservice.repository.ApplicationRepository;
import com.jobflow.applicationtrackingservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public Application createApplication(
            Long userId,
            Long jobId,
            ApplicationStatus status,
            String notes) {

        if (applicationRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new ApplicationAlreadyExistsException(
                    "Application already exists for this user and job"
            );
        }

        Application application = new Application();

        application.setUserId(userId);
        application.setJobId(jobId);
        application.setStatus(status);
        application.setNotes(notes);

        LocalDateTime now = LocalDateTime.now();

        application.setAppliedAt(now);
        application.setUpdatedAt(now);

        return applicationRepository.save(application);
    }

    @Override
    public Application getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ApplicationNotFoundException(
                                "Application not found with id: " + id
                        )
                );
    }

    @Override
    public Page<Application> getApplicationsByUser(
            Long userId,
            Pageable pageable) {

        return applicationRepository.findByUserId(userId, pageable);
    }

    @Override
    public Application updateStatus(
            Long id,
            ApplicationStatus status,
            String notes) {

        Application application = getApplicationById(id);

        application.setStatus(status);

        if (notes != null) {
            application.setNotes(notes);
        }

        application.setUpdatedAt(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(Long id) {

        Application application = getApplicationById(id);

        applicationRepository.delete(application);
    }
}