package pblweek2.megazine.dto;

import lombok.Getter;

@Getter
public class BoardChangeRequestDto {
    private String username;
    private String imageUrl;
    private String grid;
    private String content;
}
