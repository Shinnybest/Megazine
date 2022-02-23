package pblweek2.megazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.LoginRequestDto;
import pblweek2.megazine.dto.LoginResponseDto;
import pblweek2.megazine.dto.SignupRequestDto;
import pblweek2.megazine.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No ID")); // 안에 메세지를 적는다
    }


    public void registerUser(SignupRequestDto requestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        requestDto.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = new User(username, email, password);
        userRepository.save(user);
    }

    public void checkPassword(SignupRequestDto requestDto) {
        if (requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 유저네임이 들어갈 수 없습니다.");
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
        }
    }

}
