package pblweek2.megazine.entityResponse;

import lombok.Getter;

@Getter
public class PutBoard {
    private String result;
    private String msg;

    public PutBoard (String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
