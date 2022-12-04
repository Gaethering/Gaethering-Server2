package com.gaethering.modulemember.controller;

import com.gaethering.modulemember.exception.pet.ImageNotFoundException;
import com.gaethering.modulemember.service.pet.PetServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api-prefix}")
@RequiredArgsConstructor
public class PetController {

    private final PetServiceImpl petService;

    @PatchMapping("/mypage/pets/{petId}/image")
    public ResponseEntity<String> updatePetImage(@PathVariable("petId") Long id,
                                                 @RequestPart("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ImageNotFoundException();
        }

        return ResponseEntity.ok(petService.updatePetImage(id, multipartFile));
    }
}
