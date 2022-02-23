package pblweek2.megazine.entityResponse;

import lombok.Getter;

@Getter
public class CancelLikeBoard {
    private String result;
    private String msg;

    public CancelLikeBoard (String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
