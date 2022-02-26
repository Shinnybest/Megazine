package pblweek2.megazine.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.dto.SignupRequestDto;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardTest {

//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @BeforeAll
//    public void setup() throws Exception {
//
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
//        User user = new User(signupRequestDto); // -> 자동으로 id 생성?
//
//        String grid = "11";
//        String content = "111";
//        String imageUrl = "1111";
//
//        BoardRequestDto boardRequestDto = new BoardRequestDto(
//                grid,
//                content,
//                username,
//                imageUrl);
//
//        Board board = new Board(boardRequestDto);
//
//        boardRepository.save(board);
//    }

//    @Test
//    @DisplayName("유저ID를 BOARD의 COLUMN으로 매핑하기")
//    void UserMappedByBoardTable() {
//        Board board = boardRepository.findAll().get(0);
//        Long USERID = board.getUser().getId();
//        User user = userRepository.findAll().get(0);
//        Assertions.assertThat(USERID).isEqualTo(user.getId());
//        }

    }