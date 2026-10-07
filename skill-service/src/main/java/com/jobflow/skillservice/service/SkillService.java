package com.jobflow.skillservice.service;

import com.jobflow.skillservice.entity.Skill;

import java.util.List;

public interface SkillService {

    Skill createSkill(Skill skill);

    Skill getSkillById(Long id);

    List<Skill> getAllSkills();

    List<Skill> searchSkills(String name);

    Skill updateSkill(Long id, Skill skill);

    void deleteSkill(Long id);
}