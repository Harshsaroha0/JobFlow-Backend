package com.jobflow.skillservice.service;

import com.jobflow.skillservice.entity.UserSkill;

import java.util.List;

public interface UserSkillService {

    UserSkill addSkillToUser(UserSkill userSkill);

    void removeSkillFromUser(Long userId, Long skillId);

    List<UserSkill> getSkillsByUserId(Long userId);

    List<UserSkill> getUsersBySkillId(Long skillId);
}