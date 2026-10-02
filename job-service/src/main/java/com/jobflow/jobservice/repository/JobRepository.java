package com.jobflow.jobservice.repository;

import com.jobflow.jobservice.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );

    Page<Job> findByLocationContainingIgnoreCase(
            String location,
            Pageable pageable
    );

    Page<Job> findByEmploymentTypeIgnoreCase(
            String employmentType,
            Pageable pageable
    );

    Page<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String title,
            String location,
            Pageable pageable
    );

    Page<Job> findByCreatedBy(
            Long createdBy,
            Pageable pageable
    );
}