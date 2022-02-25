package pblweek2.megazine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pblweek2.megazine.dto.LoginRequestDto;
import pblweek2.megazine.dto.SignupRequestDto;
import pblweek2.megazine.entityResponse.SignupSuccess;
import pblweek2.megazine.entityResponse.Success;
import pblweek2.megazine.exception.AlreadyLoggedinException;
import pblweek2.megazine.exception.ApiRequestException;
import pblweek2.megazine.security.UserDetailsImpl;
import pblweek2.megazine.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/api/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SignupSuccess> signup_json(@Valid @RequestBody SignupRequestDto requestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            throw new AlreadyLoggedinException();
        } else {
            userService.checkPassword(requestDto);
            userService.registerUser(requestDto);
            return new ResponseEntity<>(new SignupSuccess("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
        }
    }

//    @PostMapping(value = "/api/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public ResponseEntity<SignupSuccess> signup_form(@Valid @ModelAttribute SignupRequestDto requestDto,
//                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails != null) {
//            throw new ApiRequestException("이미 회원가입된 계정입니다.");
//        } else {
//            userService.checkPassword(requestDto);
//            userService.registerUser(requestDto);
//            return new ResponseEntity<>(new SignupSuccess("success", "회원 가입 성공하였습니다."), HttpStatus.OK);
//        }
//    }

    @GetMapping("/login")
    public void getLoginPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            throw new AlreadyLoggedinException();
        }
        // (문제: 미해결) -> Spring Security 에서 제공하는 Login 페이지 HTML이 보여짐
    }

    @GetMapping("/user/signup")
    public void getSignupPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            throw new AlreadyLoggedinException();
            // (문제: 해결) -> exception error message가 정상적으로 뜬다. 위와 다른 점이 무엇일까?
        }
    }


}
