package pblweek2.megazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.LoginRequestDto;
import pblweek2.megazine.dto.SignupRequestDto;
import pblweek2.megazine.dto.UserDataResponseDto;
import pblweek2.megazine.exception_2.CustomException;
import pblweek2.megazine.exception_2.ErrorCode;
import pblweek2.megazine.repository.UserRepository;

import java.util.Optional;

import static pblweek2.megazine.exception_2.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(SignupRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new CustomException(SAME_USERNAME_EXIST);
        }
        else if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new CustomException(SAME_EMAIL_EXIST);
        }
        else {
            User user = User.builder()
                    .username(requestDto.getUsername())
                    .email(requestDto.getEmail())
                    .password(requestDto.getPassword())
                    .build();
            userRepository.save(user);
            return user;
        }
    }

    public void checkPassword(SignupRequestDto requestDto) {
        if (requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new CustomException(ErrorCode.NO_USERNAME_IN_PASSWORD);
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new CustomException(WRONG_PASSWORD_CHECK_WHEN_REGISTER);
        }
    }

    public UserDataResponseDto sendUserDataWhenLogin(LoginRequestDto loginRequestDto, String accessToken) {
        // controller 단과 겹치는데 이대로 괜찮은가?
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginRequestDto.getEmail())).orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
        Long userId = user.get().getId();
        String username = user.get().getUsername();
        String email = user.get().getEmail();
        String access_Token = accessToken;
//        String refresh_Token = refreshToken;

//        UserDataResponseDto userDataResponseDto = new UserDataResponseDto(userId, username, email, access_Token, refresh_Token);
        UserDataResponseDto userDataResponseDto = new UserDataResponseDto(userId, username, email, access_Token);
        return userDataResponseDto;
    }


}
