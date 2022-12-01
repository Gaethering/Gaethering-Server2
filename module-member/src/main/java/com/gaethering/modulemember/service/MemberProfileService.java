package com.gaethering.modulemember.service;

import com.gaethering.modulemember.dto.OtherProfileResponse;
import com.gaethering.modulemember.dto.OwnProfileResponse;

public interface MemberProfileService {

    OwnProfileResponse getOwnProfile(String email);

    OtherProfileResponse getOtherProfile(Long memberId);
}
