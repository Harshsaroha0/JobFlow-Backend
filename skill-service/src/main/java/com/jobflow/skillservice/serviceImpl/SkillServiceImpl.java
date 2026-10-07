package com.jobflow.skillservice.serviceImpl;

import com.jobflow.skillservice.entity.Skill;
import com.jobflow.skillservice.exception.SkillAlreadyExistsException;
import com.jobflow.skillservice.exception.SkillNotFoundException;
import com.jobflow.skillservice.repository.SkillRepository;
import com.jobflow.skillservice.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill createSkill(Skill skill) {
        if (skillRepository.existsByNameIgnoreCase(skill.getName())) {
            throw new SkillAlreadyExistsException("Skill already exists: " + skill.getName());
        }

        return skillRepository.save(skill);
    }

    @Override
    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found with id: " + id));
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public List<Skill> searchSkills(String name) {
        return skillRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Skill updateSkill(Long id, Skill skill) {

        Skill existingSkill = getSkillById(id);

        existingSkill.setName(skill.getName());
        existingSkill.setDescription(skill.getDescription());

        return skillRepository.save(existingSkill);
    }

    @Override
    public void deleteSkill(Long id) {
        Skill existingSkill = getSkillById(id);

        skillRepository.delete(existingSkill);
    }
}