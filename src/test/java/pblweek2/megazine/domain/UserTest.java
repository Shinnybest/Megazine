package pblweek2.megazine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pblweek2.megazine.dto.SignupRequestDto;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @Nested
    @DisplayName("새로운 유저 생성")
    class createUser {

        // given
        private String username;
        private String email;
        private String password;

        @BeforeEach
        void setup() {
            username = "deliciousharibo";
            email = "jellycompany@naver.com";
            password = "secretrecipe";
        }

        @Test
        @DisplayName("정상케이스")
        void createUser_normal() {
            //given
            String passwordCheck = password;
            SignupRequestDto requestDto = new SignupRequestDto(
                    username,
                    email,
                    password,
                    passwordCheck
            );

            //when
            User user = User.builder()
                    .username(requestDto.getUsername())
                    .email(requestDto.getEmail())
                    .password(requestDto.getPassword())
                    .build();

            //then
            assertNull(user.getId());
            assertEquals(username, user.getUsername());
            assertEquals(email, user.getEmail());
            assertEquals(password, user.getPassword());
        }
    }
}