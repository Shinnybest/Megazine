package pblweek2.megazine.entityResponse;

import lombok.Getter;

@Getter
public class DeleteBoard {
    private String result;
    private String msg;

    public DeleteBoard (String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

}
