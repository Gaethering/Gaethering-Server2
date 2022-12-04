package com.gaethering.modulemember.service;

import com.gaethering.modulemember.config.AwsS3MockConfig;
import com.gaethering.modulemember.exception.errorcode.PetErrorCode;
import com.gaethering.modulemember.exception.pet.InvalidImageTypeException;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AwsS3MockConfig.class)
@SpringBootTest
class ImageUploadServiceTest {

    @Autowired
    S3Mock s3Mock;

    @Autowired
    ImageUploadServiceImpl imageUploadService;

    @AfterEach
    public void shutdownMockS3(){
        s3Mock.stop();
    }

    @Test
    void uploadPetImageFailure_fileExtension() throws IOException {
        // given
        String filename = "test.txt";
        String contentType = "image/png";

        MockMultipartFile file = new MockMultipartFile("test", filename, contentType, "test".getBytes());

        // when
        InvalidImageTypeException exception = Assertions.assertThrows(InvalidImageTypeException.class,
                () -> imageUploadService.uploadImage(file));

        // then
        assertThat(exception.getErrorCode()).isEqualTo(PetErrorCode.INVALID_IMAGE_TYPE);
    }

    @Test
    void uploadPetImageSuccess() throws IOException {
        // given
        String filename = "test.png";
        String contentType = "image/png";
        String path = "http://127.0.0.1:8001/test-bucket/test-dir/";

        MockMultipartFile file = new MockMultipartFile("test", filename, contentType, "test".getBytes());

        // when
        String urlPath = imageUploadService.uploadImage(file);

        // then
        assertThat(urlPath.substring(0, urlPath.lastIndexOf("/") + 1)).isEqualTo(path);
    }
}