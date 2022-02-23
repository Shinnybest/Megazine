package pblweek2.megazine.entityResponse;

import lombok.Getter;
import pblweek2.megazine.dto.BoardResponseDto;

import java.util.List;

@Getter
public class ShowAllBoard {
    private String result;
    private String msg;
    private List<BoardResponseDto> data;

    public ShowAllBoard(String result, String msg, List<BoardResponseDto> data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
}
