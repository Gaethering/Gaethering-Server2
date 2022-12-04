package com.gaethering.modulemember.exception.pet;

import com.gaethering.modulemember.exception.errorcode.PetErrorCode;

public class ImageNotFoundException extends PetException {

    public ImageNotFoundException() {
        super(PetErrorCode.IMAGE_NOT_FOUND);
    }
}
