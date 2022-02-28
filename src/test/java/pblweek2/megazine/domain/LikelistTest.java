package pblweek2.megazine.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.UserRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class LikelistTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("좋아요 생성")
    void like() {
        //given
        User user = User.builder()
                .username("kitty")
                .email("hello@gmail.com")
                .password("1234")
                .build();

        User savedUser = userRepository.save(user);

        Board board = Board.builder()
                .grid("1234")
                .content("Bigbang")
                .username("kitty")
                .imageUrl("www.image.com")
                .build();

        Board savedBoard = boardRepository.save(board);

        //when
        Likelist likelist = Likelist.builder()
                .user(savedUser)
                .board(savedBoard)
                .build();

        //then
        Assertions.assertEquals(savedUser.getId(), likelist.getUser().getId());
        Assertions.assertEquals(savedBoard.getId(), likelist.getBoard().getId());
    }
}