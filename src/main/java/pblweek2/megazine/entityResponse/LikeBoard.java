package pblweek2.megazine.entityResponse;

import lombok.Getter;

@Getter
public class LikeBoard {
    private String result;
    private String msg;

    public LikeBoard (String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
