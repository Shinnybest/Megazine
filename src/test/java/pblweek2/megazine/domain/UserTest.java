package pblweek2.megazine.domain;

//import org.junit.Before;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import pblweek2.megazine.dto.BoardRequestDto;
//import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.dto.SignupRequestDto;
//import pblweek2.megazine.repository.BoardRepository;
//import pblweek2.megazine.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
            assertNull(user.getId()); // 왜 null인가?
            assertEquals(username, user.getUsername());
            assertEquals(email, user.getEmail());
            assertEquals(password, user.getPassword());
        }

        @Nested
        @DisplayName("실패케이스")
        class FailCases {
            @Nested
            @DisplayName("유저명")
            class username {
                @Test
                @DisplayName("세자리 미만")
                void fail1() {
                    //given
                    username = "ab";
                    String passwordCheck = password;
                    SignupRequestDto signupRequestDto = new SignupRequestDto(
                            username,
                            email,
                            password,
                            passwordCheck
                    );



                }
            }
        }

    }




}