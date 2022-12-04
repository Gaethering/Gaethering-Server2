package com.gaethering.modulemember.exception.pet;

import com.gaethering.modulemember.exception.errorcode.PetErrorCode;

public class PetNotFoundException extends PetException {

    public PetNotFoundException() {
        super(PetErrorCode.PET_NOT_FOUND);
    }
}
