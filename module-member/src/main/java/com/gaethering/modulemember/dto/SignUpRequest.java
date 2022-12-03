package com.gaethering.modulemember.dto;

import com.gaethering.moduledomain.domain.type.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String email;

    private String nickname;

    private String password;

    private String passwordCheck;

    private String name;

    private String phone;

    private LocalDate birth;

    private Gender gender;

}
