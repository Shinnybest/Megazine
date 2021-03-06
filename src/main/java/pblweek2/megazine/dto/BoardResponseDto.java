package pblweek2.megazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.Likelist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long boardId;
    private String creater;
    private String content;
    private String imageUrl;
    private String grid;
    private int likeCount;
    private LocalDateTime createdAt;
    private List<LikesResponseDto> likes = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.boardId = board.getId();
        this.creater = board.getUsername();
        this.content = board.getContent();
        this.imageUrl = board.getImageUrl();
        this.grid = board.getGrid();
        this.createdAt = board.getCreatedAt();

        for (Likelist each: board.getLikelist()) {
            likes.add(new LikesResponseDto(each));
        }

        this.likeCount = likes.size();
    }
}
