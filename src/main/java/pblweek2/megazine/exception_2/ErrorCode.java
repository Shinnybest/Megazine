package pblweek2.megazine.exception_2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST : 잘못된 요청
    NO_USERNAME_IN_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호에 유저명이 사용될 수 없습니다."),
    ALREADY_LOGGED_IN(HttpStatus.BAD_REQUEST, "이미 로그인한 상태입니다."),
    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "현재 로그인 상태입니다. 새로운 계정 생성이 불가능합니다."),
    WRONG_PASSWORD_CHECK_WHEN_REGISTER(HttpStatus.BAD_REQUEST, "비밀번호 중복확인이 다릅니다."),
    WRONG_PASSWORD_WHEN_LOGIN(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // 401 UNAUTHORIZED
    TOKEN_VALIDATION(HttpStatus.UNAUTHORIZED, "토큰이 없거나 만료되었습니다."),
    UNABLE_POST_BOARD(HttpStatus.UNAUTHORIZED, "작성자만 게시글 작성이 가능합니다."),
    UNABLE_UPDATE_BOARD(HttpStatus.UNAUTHORIZED, "작성자만 게시글 수정이 가능합니다."),
    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "로그인 이후 이용가능합니다."),
    UNABLE_DELETE_BOARD(HttpStatus.UNAUTHORIZED, "작성자만 게시글 삭제가 가능합니다."),

    // 404 NOT FOUND
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),


    // 409 CONFLICT (중복된 데이터 존재)
    ALREADY_LIKED_BOARD(HttpStatus.CONFLICT, "이미 좋아요 한 게시글입니다."),
    SAME_EMAIL_EXIST(HttpStatus.CONFLICT, "해당 이메일이 이미 존재합니다."),
    SAME_USERNAME_EXIST(HttpStatus.CONFLICT, "해당 유저네임이 이미 존재합니다.");


    private final HttpStatus httpStatus;
    private final String errorMessage;

}
