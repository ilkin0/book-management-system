package com.ilkinmehdiyev.usermanagement.controller;

import com.ilkinmehdiyev.usermanagement.dto.SignInRequestDto;
import com.ilkinmehdiyev.usermanagement.dto.UserRegisterRequestDto;
import com.ilkinmehdiyev.usermanagement.dto.jwt.JwtTokenResponseDto;
import com.ilkinmehdiyev.usermanagement.dto.jwt.RefreshTokenDto;
import com.ilkinmehdiyev.usermanagement.service.interfaces.AuthService;
import com.ilkinmehdiyev.usermanagement.service.interfaces.OtpService;
import com.ilkinmehdiyev.usermanagement.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ilkinmehdiyev.usermanagement.util.Const.APIS.AUTH_URL;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(AUTH_URL)
@RestController
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final OtpService otpService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserRegisterRequestDto requestDto) {
        String otp = userService.signUp(requestDto);
        return ResponseEntity.ok(otp);
    }

    @PutMapping("/sca/otp/{otpCode}")
    public ResponseEntity<Void> verifyOtp(@PathVariable String otpCode) {
        otpService.verifyOtp(otpCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtTokenResponseDto> signIn(
            @RequestBody @Valid SignInRequestDto requestDto) {
        JwtTokenResponseDto tokenResponseDto = authService.signIn(requestDto);

        var httpHeader = new HttpHeaders();
        httpHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " .concat(tokenResponseDto.accessToken().token()));

        return new ResponseEntity<>(tokenResponseDto, httpHeader, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenResponseDto> refreshAccessToken(@RequestBody @Valid RefreshTokenDto tokenDto) {

        JwtTokenResponseDto token = authService.refreshAccessToken(tokenDto);

        return ResponseEntity.ok(token);
    }
}
