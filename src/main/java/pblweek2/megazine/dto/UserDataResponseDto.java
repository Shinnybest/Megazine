package pblweek2.megazine.dto;

import lombok.Getter;

@Getter
public class UserDataResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String token;
//    private String refreshToken;
//public UserDataResponseDto(Long userId, String username, String email, String accessToken, String refreshToken) {
    public UserDataResponseDto(Long userId, String username, String email, String token) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.token = token;
//        this.refreshToken = refreshToken;
    }
}
