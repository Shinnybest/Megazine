package pblweek2.megazine.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String grid;
    private String content;
    private String username;
    private String imageUrl;

    public BoardRequestDto(String grid, String content, String username, String imageUrl) {
        this.grid = grid;
        this.content =content;
        this.username = username;
        this.imageUrl = imageUrl;
    }
}
