package pblweek2.megazine.exception_nouse;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String msg) {
        super(msg);
    }
}
