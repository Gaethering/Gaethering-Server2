package com.gaethering.modulemember.service.pet;

import org.springframework.web.multipart.MultipartFile;

public interface PetService {

    String updatePetImage(Long id, MultipartFile multipartFile);
}
