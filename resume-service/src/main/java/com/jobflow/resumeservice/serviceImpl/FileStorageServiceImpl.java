package com.jobflow.resumeservice.serviceImpl;


import com.jobflow.resumeservice.entity.Resume;
import com.jobflow.resumeservice.exception.InvalidResumeFileException;
import com.jobflow.resumeservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path storageLocation;

    public FileStorageServiceImpl(
            @Value("${file.storage.location}") String storageLocation) {

        this.storageLocation = Paths.get(storageLocation)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.storageLocation);
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Could not create storage directory",
                    exception
            );
        }
    }
    @Override
    public String storeFile(MultipartFile file, Long userId) {

        if (file == null || file.isEmpty()) {
            throw new InvalidResumeFileException("Resume file is required");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new InvalidResumeFileException("File name is missing");
        }

        String extension = "";

        int lastDot = originalFileName.lastIndexOf('.');

        if (lastDot != -1) {
            extension = originalFileName
                    .substring(lastDot)
                    .toLowerCase();
        }

        if (!extension.equals(".pdf") && !extension.equals(".docx")) {
            throw new InvalidResumeFileException(
                    "Only PDF and DOCX files are allowed"
            );
        }

        try {

            String storedFileName =
                    UUID.randomUUID() + extension;

            Path userDirectory =
                    storageLocation.resolve("user-" + userId);

            Files.createDirectories(userDirectory);

            Path targetLocation =
                    userDirectory
                            .resolve(storedFileName)
                            .normalize();

            if (!targetLocation.startsWith(userDirectory)) {
                throw new InvalidResumeFileException(
                        "Invalid file path"
                );
            }

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetLocation.toString();

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Could not store resume file",
                    exception
            );
        }
    }

    @Override
    public Resource loadFile(String filePath) {

        try {
            Path path = Paths.get(filePath)
                    .toAbsolutePath()
                    .normalize();

            Resource resource =
                    new org.springframework.core.io.UrlResource(
                            path.toUri()
                    );

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("File not found");
            }

            return resource;

        } catch (Exception exception) {
            throw new RuntimeException(
                    "Could not load resume file",
                    exception
            );
        }
    }

    @Override
    public void deleteFile(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            return;
        }

        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Could not delete file",
                    exception
            );
        }
    }
}