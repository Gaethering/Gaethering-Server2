package com.gaethering.modulemember.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    String uploadImage(MultipartFile multipartFile);

    void removeImage(String filename);

    String createFileName(String filename);

    String getFileExtension(String filename);

    void validateFileExtension(String filename);
}
