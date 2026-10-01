package com.jobflow.resumeservice.repository;

import com.jobflow.resumeservice.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUserIdOrderByVersionAsc(Long userId);
    Optional<Resume> findTopByUserIdOrderByVersionDesc(Long userId);
}

