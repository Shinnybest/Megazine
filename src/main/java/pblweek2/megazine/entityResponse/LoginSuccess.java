package pblweek2.megazine.entityResponse;

import lombok.Getter;
import pblweek2.megazine.dto.UserDataResponseDto;

@Getter
public class LoginSuccess {
    private String result;
    private String msg;
    private UserDataResponseDto userData;

    public LoginSuccess (UserDataResponseDto userData) {
        this.result = "success";
        this.msg = "로그인 성공하였습니다.";
        this.userData = userData;
    }
}
