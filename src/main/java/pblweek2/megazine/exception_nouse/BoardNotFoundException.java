package pblweek2.megazine.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String msg) {
        super(msg);
    }
}
