package com.gaethering.modulemember.controller;

import com.gaethering.modulemember.dto.ConfirmEmailRequest;
import com.gaethering.modulemember.dto.ConfirmEmailResponse;
import com.gaethering.modulemember.dto.EmailAuthRequest;
import com.gaethering.modulemember.dto.SignUpRequest;
import com.gaethering.modulemember.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${member-api-prefix}")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {

        memberService.signUp(signUpRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/email-auth")
    public ResponseEntity<Void> sendEmailAuthCode(@RequestBody EmailAuthRequest emailAuthRequest) {
        memberService.sendEmailAuthCode(emailAuthRequest.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/email-confirm")
    public ResponseEntity<ConfirmEmailResponse> confirmEmailAuthCode(
        @RequestBody ConfirmEmailRequest confirmEmailRequest
    ) {
        memberService.confirmEmailAuthCode(confirmEmailRequest.getCode());

        return ResponseEntity.ok(ConfirmEmailResponse.builder()
            .emailAuth(true)
            .build());
    }
}
