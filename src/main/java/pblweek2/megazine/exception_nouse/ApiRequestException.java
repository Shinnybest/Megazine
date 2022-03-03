package pblweek2.megazine.exception;

public class ApiRequestException extends RuntimeException {
    public ApiRequestException(String msg) {
        super(msg);
    }
}
