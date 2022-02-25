package pblweek2.megazine.domain;

//import org.junit.Before;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import pblweek2.megazine.dto.BoardRequestDto;
//import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.dto.SignupRequestDto;
//import pblweek2.megazine.repository.BoardRepository;
//import pblweek2.megazine.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {
    @Test
    @DisplayName("새로운 유저 생성")
    void createUser() {

        // given
        String username = "testuser";
        String email = "kim@gmail.com";
        String password = "test1234";

        SignupRequestDto signupRequestDto = new SignupRequestDto(
                username,
                email,
                password
        );

        // when
        User user = new User(
                signupRequestDto
        );

        // then
        assertNull();
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Before
//    public void setup() throws Exception {
//        String username = "testuser";
//        String email = "kim@gmail.com";
//        String password = "test1234";
//
//        SignupRequestDto signupRequestDto = new SignupRequestDto(
//                username,
//                email,
//                password
//        );
//
//        User user = new User(signupRequestDto);
//
//        String grid = "11";
//        String content = "111";
//        String imageUrl = "1111";
//
//        BoardRequestDto boardRequestDto = new BoardRequestDto(grid, content, username, imageUrl);
//        Board board = new Board(boardRequestDto);
//
//        boardRepository.save(board);
//    }
//
//    @Test
//    @DisplayName("유저ID를 BOARD의 COLUMN으로 매핑하기")
//    void UserMappedByBoardTable() {
//        Board board = boardRepository.findAll().get(0);
//        Long USERID = board.getUser().getId();
//        assertThat(USERID).isEqualTo("member1");
//
//        Team team = member.getTeam();
//        assertThat(team.getName()).isEqualTo("TeamA");
//
//        List<Member> members = team.getMembers();
//        for (Member m : members) {
//            assertThat(m.getUsername()).startsWith("member");
//        }
//
//
//
//
//        // given
//
//
//
//        // when
//
//
//        // then
//
//    }


}