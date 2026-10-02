package com.jobflow.jobservice.service;

import com.jobflow.jobservice.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    Job createJob(Job job);

    Job getJobById(Long id);

    Page<Job> getAllJobs(Pageable pageable);

    Page<Job> searchJobs(
            String title,
            String location,
            String employmentType,
            Pageable pageable
    );

    Page<Job> getJobsByUser(Long userId, Pageable pageable);

    Job updateJob(Long id, Job job, Long userId, String role);

    void deleteJob(Long id, Long userId, String role);
}