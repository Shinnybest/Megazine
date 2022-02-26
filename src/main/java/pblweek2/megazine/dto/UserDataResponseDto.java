package pblweek2.megazine.dto;

import lombok.Getter;

@Getter
public class UserDataResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;

    public UserDataResponseDto(Long userId, String username, String email, String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
