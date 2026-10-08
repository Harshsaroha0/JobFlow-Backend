package com.jobflow.skillservice.repository;

import com.jobflow.skillservice.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);

    List<UserSkill> findByUserId(Long userId);

    List<UserSkill> findBySkillId(Long skillId);
}