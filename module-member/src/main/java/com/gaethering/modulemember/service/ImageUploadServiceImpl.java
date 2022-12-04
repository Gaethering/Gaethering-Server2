package com.gaethering.modulemember.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gaethering.modulemember.exception.pet.FailedUploadImageException;
import com.gaethering.modulemember.exception.pet.ImageNotFoundException;
import com.gaethering.modulemember.exception.pet.InvalidImageTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${dir}")
    private String dir;

    @Override
    public String uploadPetImage(MultipartFile multipartFile) {

        String fileName = createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(new PutObjectRequest(bucket + "/" + dir, fileName, multipartFile.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch(IOException e) {
            throw new FailedUploadImageException();
        }

        return amazonS3.getUrl(bucket, dir + "/" + fileName).toString();
    }

    @Override
    public void removePetImage(String filename) {
        amazonS3.deleteObject(bucket,
                dir + "/" + filename.substring( filename.lastIndexOf("/") + 1));
    }

    @Override
    public String createFileName(String filename) {
        return UUID.randomUUID().toString().concat(getFileExtension(filename));
    }

    @Override
    public String getFileExtension(String filename) {
        if (filename.length() == 0) {
            throw new ImageNotFoundException();
        }
        validateFileExtension(filename);

        return filename.substring(filename.lastIndexOf("."));
    }

    @Override
    public void validateFileExtension(String filename) {
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");

        String idxFileName = filename.substring(filename.lastIndexOf("."));

        if (!fileValidate.contains(idxFileName)) {
            throw new InvalidImageTypeException();
        }
    }
}
