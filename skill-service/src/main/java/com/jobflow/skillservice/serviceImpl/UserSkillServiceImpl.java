package com.jobflow.skillservice.serviceImpl;

import com.jobflow.skillservice.entity.UserSkill;
import com.jobflow.skillservice.exception.UserSkillAlreadyExistsException;
import com.jobflow.skillservice.exception.UserSkillNotFoundException;
import com.jobflow.skillservice.repository.UserSkillRepository;
import com.jobflow.skillservice.service.UserSkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;

    public UserSkillServiceImpl(UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    @Override
    public UserSkill addSkillToUser(UserSkill userSkill) {

        if (userSkillRepository.existsByUserIdAndSkillId(
                userSkill.getUserId(),
                userSkill.getSkillId())) {

            throw new UserSkillAlreadyExistsException(
                    "Skill already assigned to user"
            );
        }

        return userSkillRepository.save(userSkill);
    }

    @Override
    public void removeSkillFromUser(Long userId, Long skillId) {

        UserSkill userSkill = userSkillRepository
                .findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() ->
                        new UserSkillNotFoundException(
                                "User skill relationship not found"
                        ));

        userSkillRepository.delete(userSkill);
    }

    @Override
    public List<UserSkill> getSkillsByUserId(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }

    @Override
    public List<UserSkill> getUsersBySkillId(Long skillId) {
        return userSkillRepository.findBySkillId(skillId);
    }
}