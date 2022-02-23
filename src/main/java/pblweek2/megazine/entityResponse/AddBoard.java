package pblweek2.megazine.entityResponse;

import lombok.Getter;

@Getter
public class AddBoard {
    private String result;
    private String msg;
    private Long boardId;

    public AddBoard(String result, String msg, Long boardId) {
        this.result = result;
        this.msg = msg;
        this.boardId = boardId;

    }
}
