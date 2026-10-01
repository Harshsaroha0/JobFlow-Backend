package com.jobflow.resumeservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file, Long userId);

    Resource loadFile(String filePath);

    void deleteFile(String filePath);
}