package pblweek2.megazine.entityResponse;

import lombok.Getter;
import pblweek2.megazine.dto.BoardResponseDto;

@Getter
public class ReadOneBoard {
    private String result;
    private String msg;
    private BoardResponseDto data;

    public ReadOneBoard(String result, String msg, BoardResponseDto data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
}
