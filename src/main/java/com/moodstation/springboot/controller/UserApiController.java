package com.moodstation.springboot.controller;

import com.moodstation.springboot.dto.Message;
import com.moodstation.springboot.dto.request.CheckEmailDto;
import com.moodstation.springboot.dto.request.CheckNicknameDto;
import com.moodstation.springboot.dto.request.LoginDto;
import com.moodstation.springboot.dto.response.LoginResponse;
import com.moodstation.springboot.dto.response.UserData;
import com.moodstation.springboot.dto.request.SignupDto;
import com.moodstation.springboot.enums.ErrorCode;
import com.moodstation.springboot.enums.StatusEnum;
import com.moodstation.springboot.exception.ConflictExecption;
import com.moodstation.springboot.service.AuthService;
import com.moodstation.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.charset.Charset;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody @Valid SignupDto signupDto) {
        UserData body = authService.signup(signupDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("회원가입 성공");
        message.setData(body);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 회원가입 이메일 중복 체크
    @PostMapping("/check/email")
    public Boolean emailCheck(@RequestBody @Valid CheckEmailDto checkEmailDto) {
        if (!userService.checkEmail(checkEmailDto.getEmail())) {
            throw new ConflictExecption("이메일이 중복되었습니다.", ErrorCode.CONFLICT, HttpStatus.CONFLICT);
        }

        return false;
    }

    @PostMapping("/check/nickname")
    public Boolean nicknameCheck(@RequestBody CheckNicknameDto checkNicknameDto) {
        if (!userService.checkNickname(checkNicknameDto.getNickname())) {
            throw new ConflictExecption("닉네임이 중복되었습니다.", ErrorCode.CONFLICT, HttpStatus.CONFLICT);
        }

        return false;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginDto loginDto) {
        LoginResponse body = authService.login(loginDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("로그인 성공");
        message.setData(body);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
