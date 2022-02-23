package pblweek2.megazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pblweek2.megazine.domain.User;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String username;
    private String email;

    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
