package pblweek2.megazine.entityResponse;

import lombok.Getter;

@Getter
public class SignupSuccess {
    private String result;
    private String msg;

    public SignupSuccess (String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
