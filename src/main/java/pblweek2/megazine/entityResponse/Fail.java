package pblweek2.megazine.entityResponse;

import lombok.Getter;

@Getter
public class Fail {
    private String result;
    private String msg;

    public Fail(String msg) {
        this.result = "fail";
        this.msg = msg;
    }
}
