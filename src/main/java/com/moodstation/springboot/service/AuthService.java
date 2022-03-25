package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.request.LoginDto;
import com.moodstation.springboot.dto.response.LoginResponse;
import com.moodstation.springboot.dto.response.UserData;
import com.moodstation.springboot.dto.request.SignupDto;
import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.enums.ErrorCode;
import com.moodstation.springboot.exception.ConflictExecption;
import com.moodstation.springboot.jwt.JwtGenerator;
import com.moodstation.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder bcryptEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtGenerator jwtGenerator;

    @Transactional
    public UserData signup(SignupDto signupDto) {
        if(!userService.checkEmail(signupDto.getEmail())) {
            System.out.println(userService.checkEmail(signupDto.getEmail()));
            throw new ConflictExecption("이메일이 중복되었습니다.", ErrorCode.CONFLICT, HttpStatus.CONFLICT);
        }

        if(!userService.checkNickname(signupDto.getNickname())) {
            throw new ConflictExecption("닉네임이 중복되었습니다.", ErrorCode.CONFLICT, HttpStatus.CONFLICT);
        }

        String password = bcryptEncoder.encode(signupDto.getPassword());
        User user = User.builder()
                .email(signupDto.getEmail())
                .nickname(signupDto.getNickname())
                .password(password)
                .build();

        user = userService.createUser(user);
        return UserData.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    public LoginResponse login(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String accessToken = jwtGenerator.generateAccessToken(userDetails);
        final String refreshToken = jwtGenerator.generateRefreshToken(request.getEmail());

        return LoginResponse.builder()
                .userId(userRepository.findUserByEmail(request.getEmail()).getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
