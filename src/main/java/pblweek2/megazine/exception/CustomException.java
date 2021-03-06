package pblweek2.megazine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만들어줍니다. <-> RequiredArgsConstructor, NoArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
