package com.jobflow.resumeservice.serviceImpl;

import com.jobflow.resumeservice.entity.Resume;
import com.jobflow.resumeservice.exception.ResumeNotFoundException;
import com.jobflow.resumeservice.repository.ResumeRepository;
import com.jobflow.resumeservice.service.FileStorageService;
import com.jobflow.resumeservice.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final FileStorageService fileStorageService;

    @Override
    public Resume uploadResume(Long userId, MultipartFile file) {

        Optional<Resume> latestResume =
                resumeRepository.findTopByUserIdOrderByVersionDesc(userId);

        int nextVersion = latestResume
                .map(resume -> resume.getVersion() + 1)
                .orElse(1);

        String filePath =
                fileStorageService.storeFile(file, userId);

        Resume resume = new Resume();

        resume.setUserId(userId);
        resume.setFileName(file.getOriginalFilename());
        resume.setFilePath(filePath);
        resume.setFileType(file.getContentType());
        resume.setFileSize(file.getSize());
        resume.setVersion(nextVersion);

        return resumeRepository.save(resume);
    }

    @Override
    public List<Resume> getResumesByUserId(Long userId) {
        return resumeRepository.findByUserIdOrderByVersionAsc(userId);
    }

    @Override
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() ->
                        new ResumeNotFoundException(
                                "Resume not found with id: " + id));
    }


    @Override
    public Resource downloadResume(Long id) {

        Resume resume = getResumeById(id);

        return fileStorageService.loadFile(
                resume.getFilePath()
        );
    }

    @Override
    public void deleteResume(Long id) {

        Resume existingResume = getResumeById(id);

        fileStorageService.deleteFile(
                existingResume.getFilePath()
        );

        resumeRepository.delete(existingResume);
    }
}