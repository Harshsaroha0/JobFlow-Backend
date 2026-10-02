package com.jobflow.jobservice.serviceImpl;

import com.jobflow.jobservice.entity.Job;
import com.jobflow.jobservice.exception.JobNotFoundException;
import com.jobflow.jobservice.exception.UnauthorizedJobAccessException;
import com.jobflow.jobservice.repository.JobRepository;
import com.jobflow.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public Job createJob(Job job) {

        LocalDateTime now = LocalDateTime.now();

        job.setCreatedAt(now);
        job.setUpdatedAt(now);

        return jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found with id: " + id
                        )
                );
    }

    @Override
    public Page<Job> getAllJobs(Pageable pageable) {

        return jobRepository.findAll(pageable);
    }

    @Override
    public Page<Job> searchJobs(
            String title,
            String location,
            String employmentType,
            Pageable pageable) {

        if (title != null && !title.isBlank()
                && location != null && !location.isBlank()) {

            return jobRepository
                    .findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
                            title,
                            location,
                            pageable
                    );
        }

        if (title != null && !title.isBlank()) {

            return jobRepository
                    .findByTitleContainingIgnoreCase(
                            title,
                            pageable
                    );
        }

        if (location != null && !location.isBlank()) {

            return jobRepository
                    .findByLocationContainingIgnoreCase(
                            location,
                            pageable
                    );
        }

        if (employmentType != null && !employmentType.isBlank()) {

            return jobRepository
                    .findByEmploymentTypeIgnoreCase(
                            employmentType,
                            pageable
                    );
        }

        return jobRepository.findAll(pageable);
    }

    @Override
    public Page<Job> getJobsByUser(
            Long userId,
            Pageable pageable) {

        return jobRepository.findByCreatedBy(
                userId,
                pageable
        );
    }

    @Override
    public Job updateJob(
            Long id,
            Job job,
            Long userId,
            String role) {

        Job existingJob = getJobById(id);

        checkOwnership(existingJob, userId, role);

        existingJob.setTitle(job.getTitle());
        existingJob.setDescription(job.getDescription());
        existingJob.setCompanyName(job.getCompanyName());
        existingJob.setLocation(job.getLocation());
        existingJob.setEmploymentType(job.getEmploymentType());
        existingJob.setExperienceMin(job.getExperienceMin());
        existingJob.setExperienceMax(job.getExperienceMax());
        existingJob.setSalaryMin(job.getSalaryMin());
        existingJob.setSalaryMax(job.getSalaryMax());

        existingJob.setUpdatedAt(LocalDateTime.now());

        return jobRepository.save(existingJob);
    }

    @Override
    public void deleteJob(
            Long id,
            Long userId,
            String role) {

        Job existingJob = getJobById(id);

        checkOwnership(existingJob, userId, role);

        jobRepository.delete(existingJob);
    }

    private void checkOwnership(
            Job job,
            Long userId,
            String role) {

        if ("ADMIN".equalsIgnoreCase(role)) {
            return;
        }

        if (!job.getCreatedBy().equals(userId)) {
            throw new UnauthorizedJobAccessException(
                    "You are not authorized to modify this job"
            );
        }
    }
}