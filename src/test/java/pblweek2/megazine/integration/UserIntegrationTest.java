package pblweek2.megazine.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.controller.UserController;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.LoginRequestDto;
import pblweek2.megazine.dto.SignupRequestDto;
import pblweek2.megazine.dto.UserDataResponseDto;
import pblweek2.megazine.entityResponse.LoginSuccess;
import pblweek2.megazine.exception.CustomException;
import pblweek2.megazine.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserIntegrationTest {
    @Autowired
    UserService userService;

    @Autowired
    UserController userController;

    @Test
    @Order(1)
    @DisplayName("회원가입-성공")
    void test1 () {
        //given
        String username = "testcode";
        String email = "testcode@korea.co.kr";
        String password = "test1234";
        String passwordCheck = "test1234";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        User user = userService.registerUser(requestDto);

        //then
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(email, user.getEmail());

    }

    @Test
    @Order(2)
    @DisplayName("회원가입-실패(유저네임 중복)")
    void fail_signup_1() {
        //given
        String username = "love";
        String email = "user@naver.com";
        String password = "test1234";
        String passwordCheck = "test1234";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userService.registerUser(requestDto));

        //then
        Assertions.assertEquals("해당 유저네임이 이미 존재합니다.", exception.getErrorCode().getErrorMessage());
    }

    @Test
    @Order(3)
    @DisplayName("회원가입-실패(이메일 중복)")
    void fail_signup_2() {
        //given
        String username = "kim";
        String email = "love@naver.com";
        String password = "test1234";
        String passwordCheck = "test1234";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userService.registerUser(requestDto));

        //then
        Assertions.assertEquals("해당 이메일이 이미 존재합니다.", exception.getErrorCode().getErrorMessage());
    }

    @Test
    @Order(4)
    @DisplayName("회원가입-실패(USERNAME in PASSWORD)")
    void fail_signup_3() {
        //given
        String username = "kim";
        String email = "love@naver.com";
        String password = "kim1234";
        String passwordCheck = "kim1234";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userService.checkPassword(requestDto));

        //then
        Assertions.assertEquals("비밀번호에 유저명이 사용될 수 없습니다.", exception.getErrorCode().getErrorMessage());
    }

    @Test
    @Order(5)
    @DisplayName("회원가입-실패(비밀번호 != 비밀번호 중복확인)")
    void fail_signup_4() {
        //given
        String username = "hello";
        String email = "love@naver.com";
        String password = "kim1234";
        String passwordCheck = "kim12341";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userService.checkPassword(requestDto));

        //then
        Assertions.assertEquals("비밀번호 중복확인이 다릅니다.", exception.getErrorCode().getErrorMessage());
    }


    @Test
    @Order(6)
    @DisplayName("로그인-성공")
    void test2 () {
        //given
        String username = "testcode";
        String email = "testcode@korea.co.kr";
        String password = "test1234";
        String passwordCheck = "test1234";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        User user = userService.registerUser(requestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto(user.getEmail(), password);

        //when
        ResponseEntity<LoginSuccess> success = userController.login(loginRequestDto);

        //then
        UserDataResponseDto userData = success.getBody().getUserData();

        Assertions.assertEquals(user.getId(), userData.getUserId());
        Assertions.assertEquals(user.getUsername(), userData.getUsername());
        Assertions.assertEquals(user.getEmail(), userData.getEmail());
        Assertions.assertEquals(false, userData.getToken().isEmpty());
    }

    @Test
    @Order(7)
    @DisplayName("로그인-실패(존재하지 않는 이메일)")
    void fail_login_1 () {
        //given
        String username = "testcode";
        String email = "testcode@korea.co.kr";
        String password = "test1234";
        String passwordCheck = "test1234";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        User user = userService.registerUser(requestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto(user.getEmail() + "add", password);

        //when
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userController.login(loginRequestDto));

        //then
        Assertions.assertEquals("존재하지 않는 사용자입니다.", exception.getErrorCode().getErrorMessage());
    }

    @Test
    @Order(8)
    @DisplayName("로그인-실패(잘못된 비밀번호)")
    void fail_login_2 () {
        //given
        String username = "testcode";
        String email = "testcode@korea.co.kr";
        String password = "test1234";
        String passwordCheck = "test1234";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                email,
                password,
                passwordCheck
        );

        //when
        User user = userService.registerUser(requestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto(user.getEmail(), password + "add");

        //when
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> userController.login(loginRequestDto));

        //then
        Assertions.assertEquals("비밀번호가 일치하지 않습니다.", exception.getErrorCode().getErrorMessage());
    }
}
