package pblweek2.megazine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pblweek2.megazine.dto.LoginRequestDto;
import pblweek2.megazine.dto.SignupRequestDto;
import pblweek2.megazine.entityResponse.LoginSuccess;
import pblweek2.megazine.entityResponse.SignupSuccess;
import pblweek2.megazine.exception.UserNotFoundException;
import pblweek2.megazine.exception.WrongPasswordException;
import pblweek2.megazine.repository.UserRepository;
import pblweek2.megazine.jwt.JwtTokenProvider;
import pblweek2.megazine.service.UserService;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/api/register")
    public ResponseEntity<SignupSuccess> join(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.checkPassword(signupRequestDto);
        userService.registerUser(signupRequestDto);
        return new ResponseEntity<>(new SignupSuccess("success", "회원가입 성공했습니다."), HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginSuccess> login(@RequestBody LoginRequestDto loginRequestDto) {
        pblweek2.megazine.domain.User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        Long AccessTokenValidTime = 30 * 60 * 1000L;
        Long RefreshTokenValidTime = 10080 * 60 * 1000L;

        String accessToken = jwtTokenProvider.createToken(user.getEmail(), AccessTokenValidTime);
        String refreshToken = jwtTokenProvider.createToken(user.getEmail(), RefreshTokenValidTime);
        userRepository.updateRefreshToken(loginRequestDto.getEmail(), refreshToken);
        return new ResponseEntity<>(new LoginSuccess(userService.sendUserDataWhenLogin(loginRequestDto, accessToken, refreshToken)), HttpStatus.OK);
    }

}
