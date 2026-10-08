package com.jobflow.skillservice.controller;

import com.jobflow.skillservice.entity.UserSkill;
import com.jobflow.skillservice.service.UserSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
public class UserSkillController {

    private final UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @PostMapping
    public ResponseEntity<UserSkill> addSkillToUser(
            @Valid @RequestBody UserSkill userSkill) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userSkillService.addSkillToUser(userSkill));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeSkillFromUser(
            @RequestParam Long userId,
            @RequestParam Long skillId) {

        userSkillService.removeSkillFromUser(userId, skillId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSkill>> getSkillsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userSkillService.getSkillsByUserId(userId)
        );
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<UserSkill>> getUsersBySkillId(
            @PathVariable Long skillId) {

        return ResponseEntity.ok(
                userSkillService.getUsersBySkillId(skillId)
        );
    }
}