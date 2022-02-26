package pblweek2.megazine.controller;

import groovy.util.logging.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pblweek2.megazine.entityResponse.Fail;
import pblweek2.megazine.exception.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Fail> UserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new Fail("해당 사용자는 존재하지 않습니다."), HttpStatus.OK);
    }

    @ExceptionHandler({BoardNotFoundException.class})
    public ResponseEntity<Fail> BoardNotFoundException(BoardNotFoundException ex) {
        return new ResponseEntity<>(new Fail("해당 게시글은 존재하지 않습니다."), HttpStatus.OK);
    }

    @ExceptionHandler({UserNotLoginException.class})
    public ResponseEntity<Fail> UserNotLoginException(UserNotLoginException ex) {
        return new ResponseEntity<>(new Fail("접근 권한이 없습니다. 우선, 로그인해주세요."), HttpStatus.OK);
    }

    @ExceptionHandler({AlreadyLoggedinException.class})
    public ResponseEntity<Fail> AlreadyLoggedinException(AlreadyLoggedinException ex) {
        return new ResponseEntity<>(new Fail("이미 로그인된 상태입니다."), HttpStatus.OK);
    }

    @ExceptionHandler({AlreadyRegisteredException.class})
    public ResponseEntity<Fail> AlreadyRegisteredException(AlreadyRegisteredException ex) {
        return new ResponseEntity<>(new Fail("이미 회원가입된 계정입니다."), HttpStatus.OK);
    }

    @ExceptionHandler({UnableToupdateBoardException.class})
    public ResponseEntity<Fail> UnableToupdateBoardException(UnableToupdateBoardException ex) {
        return new ResponseEntity<>(new Fail("글은 작성자만 수정할 수 있습니다."), HttpStatus.OK);
    }

    @ExceptionHandler({AlreadyLikedException.class})
    public ResponseEntity<Fail> AlreadyLikedException(AlreadyLikedException ex) {
        return new ResponseEntity<>(new Fail("이미 좋아요 한 게시물입니다."), HttpStatus.OK);
    }

    @ExceptionHandler({TokenValidationException.class})
    public ResponseEntity<Fail> TokenValidationException(TokenValidationException ex) {
        return new ResponseEntity<>(new Fail("토큰이 잘못되었거나 만료되었습니다."), HttpStatus.OK);
    }

    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity<Fail> WrongPasswordException(WrongPasswordException ex) {
        return new ResponseEntity<>(new Fail("비밀번호 입력이 잘못되었습니다. 다시 입력해주세요."), HttpStatus.OK);
    }

    @ExceptionHandler({NoUsernameInPassword.class})
    public ResponseEntity<Fail> NoUsernameInPassword(NoUsernameInPassword ex) {
        return new ResponseEntity<>(new Fail("유저명을 비밀번호로 사용할 수 없습니다."), HttpStatus.OK);
    }

    @ExceptionHandler({PasswordCheckTwiceException.class})
    public ResponseEntity<Fail> PasswordCheckTwiceException(PasswordCheckTwiceException ex) {
        return new ResponseEntity<>(new Fail("비밀번호 확인이 일치하지 않습니다."), HttpStatus.OK);
    }





}
