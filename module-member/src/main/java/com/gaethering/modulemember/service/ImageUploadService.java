package com.gaethering.modulemember.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    String uploadPetImage(MultipartFile multipartFile);

    void removePetImage(String filename);

    String createFileName(String filename);

    String getFileExtension(String filename);

    void validateFileExtension(String filename);
}
