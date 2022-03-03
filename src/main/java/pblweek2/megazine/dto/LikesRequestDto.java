package pblweek2.megazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesRequestDto {
    private Long userId;

    public LikesRequestDto(Long userId) {
        this.userId = userId;
    }
}
