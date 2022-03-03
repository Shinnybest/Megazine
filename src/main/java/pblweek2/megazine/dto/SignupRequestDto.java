package pblweek2.megazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Length(min = 3, message = "최소 3자 이상 글자를 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min = 4)
    private String password;

    @NotBlank(message = "비밀번호를 다시 한번 입력해주세요.")
    private String passwordCheck;

    public SignupRequestDto(String username, String email, String password, String passwordCheck) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
