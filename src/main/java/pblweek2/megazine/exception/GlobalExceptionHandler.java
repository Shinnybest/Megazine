package pblweek2.megazine.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
//    public ResponseEntity handleException(Exception ex) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setResult("fail");
//        errorResponse.setMsg(ex.getMessage());
//        return new ResponseEntity(
//                // HTTP body
//                errorResponse,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler({ CustomException.class })
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        return ErrorResponse.toResponseEntity(ex.getErrorCode());
    }

}
