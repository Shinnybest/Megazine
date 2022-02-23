package pblweek2.megazine.dto;

import lombok.Getter;

@Getter
public class UserDataResponseDto {
    private Long userId;
    private String username;
    private String email;

    public UserDataResponseDto(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
