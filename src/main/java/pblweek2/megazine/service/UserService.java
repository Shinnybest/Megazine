package pblweek2.megazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.LoginRequestDto;
import pblweek2.megazine.dto.SignupRequestDto;
import pblweek2.megazine.dto.UserDataResponseDto;
import pblweek2.megazine.exception.AlreadyRegisteredException;
import pblweek2.megazine.exception.NoUsernameInPassword;
import pblweek2.megazine.exception.PasswordCheckTwiceException;
import pblweek2.megazine.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public void registerUser(SignupRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent() || userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new AlreadyRegisteredException();
        } else {
            User user = new User(requestDto);
            userRepository.save(user);
        }
    }

    public void checkPassword(SignupRequestDto requestDto) {
        if (requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new NoUsernameInPassword();
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new PasswordCheckTwiceException();
        }
    }

    public UserDataResponseDto sendUserDataWhenLogin(LoginRequestDto loginRequestDto, String accessToken) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginRequestDto.getEmail())).orElseThrow(NullPointerException::new);
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
