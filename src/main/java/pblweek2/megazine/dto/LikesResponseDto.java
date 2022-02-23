package pblweek2.megazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pblweek2.megazine.domain.Likelist;

@NoArgsConstructor
@Getter
public class LikesResponseDto {
    private Long userId;

    public LikesResponseDto(Likelist likelist) {
        this.userId = likelist.getUser().getId();
    }
}
