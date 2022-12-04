package com.gaethering.modulemember.exception.pet;

import com.gaethering.modulemember.exception.errorcode.PetErrorCode;

public class FailedUploadImageException extends PetException {

    public FailedUploadImageException() {
        super(PetErrorCode.FAILED_UPLOAD_IMAGE);
    }
}
