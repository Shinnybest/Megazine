package pblweek2.megazine.exception_2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private String result;
    private String msg;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .result("fail")
                        .msg(errorCode.getErrorMessage())
                        .build());
    }
}
