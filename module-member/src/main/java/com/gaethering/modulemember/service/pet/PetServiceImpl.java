package com.gaethering.modulemember.service.pet;

import com.gaethering.moduledomain.domain.member.Pet;
import com.gaethering.moduledomain.repository.pet.PetRepository;
import com.gaethering.modulemember.exception.pet.ImageNotFoundException;
import com.gaethering.modulemember.exception.pet.PetNotFoundException;
import com.gaethering.modulemember.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final ImageUploadService imageUploadService;
    private final PetRepository petRepository;

    @Value("${default.image-url}")
    private String defaultImageUrl;

    @Override
    public String updatePetImage(Long id, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ImageNotFoundException();
        }

        Pet pet = petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);

        if (!defaultImageUrl.equals(pet.getImageUrl())) {
            imageUploadService.removeImage(pet.getImageUrl());
        }

        String newImageUrl = imageUploadService.uploadImage(multipartFile);
        pet.updateImage(newImageUrl);

        return newImageUrl;
    }

}
