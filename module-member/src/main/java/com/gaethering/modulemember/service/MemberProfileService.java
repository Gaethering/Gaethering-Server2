package com.gaethering.modulemember.service;

import com.gaethering.modulemember.dto.OwnProfileResponse;

public interface MemberProfileService {

    OwnProfileResponse getOwnProfile(String email);
}
