package pblweek2.megazine.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestDtoTest {
    @Test
    @DisplayName("정상 케이스")
    void createSignupDto() {
        //given
        String username = "testuser";
        String email = "test@naver.com";
        String password = "test1234";
        String passwordCheck = "test1234";

        //when
        SignupRequestDto dto = new SignupRequestDto(username, email, password, passwordCheck);

        //then
        assertEquals(username, dto.getUsername());
        assertEquals(email, dto.getEmail());
        assertEquals(password, dto.getPassword());
        assertEquals(passwordCheck, dto.getPasswordCheck());

    }

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("유효성 논리 동작여부")
    public void shouldReturnViolation() {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUsername("username");

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);

        Assertions.assertThat(violations).isNotEmpty();
    }
//    @Nested
//    @DisplayName("실패케이스")
//    @WebMvcTest
//    class test {
//        @Autowired
//        private Validator validatorInjected;
//
//        @Test
//        @DisplayName("Valid Test")
//        void validUsername() {
//            String username = "ab";
//            String email = "ab@naver.com";
//            String password = "1234";
//            String passwordCheck = "1234";
//
//            SignupRequestDto dto = new SignupRequestDto(username, email, password, passwordCheck);
//
//            // when
//            Set<ConstraintViolation<SignupRequestDto>> validate = validatorInjected.validate(dto);
//
//            // then
//            Iterator<ConstraintViolation<SignupRequestDto>> iterator = validate.iterator();
//            List<String> messages = new ArrayList<>();
//            while (iterator.hasNext()) {
//                ConstraintViolation<SignupRequestDto> next = iterator.next();
//                messages.add(next.getMessage());
//                System.out.println("message = " + next.getMessage());
//            }
//
//            Assertions.assertThat(messages).contains("최소 3자 이상");
//        }
//    }
}