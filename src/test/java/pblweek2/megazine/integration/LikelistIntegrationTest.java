package pblweek2.megazine.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.Likelist;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.LikesRequestDto;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.LikesRepository;
import pblweek2.megazine.repository.UserRepository;
import pblweek2.megazine.service.LikesService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class LikelistIntegrationTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    LikesService likesService;


    @Test
    @DisplayName("좋아요 생성")
    void postLike() {
        //given
        User user = User.builder()
                .username("username")
                .email("na@gmail.com")
                .password("12333")
                .build();

        Board board = Board.builder()
                .grid("1234")
                .content("이름이 뭐니?")
                .username("helloworld")
                .imageUrl("image.com")
                .build();

        User savedUser = userRepository.save(user);
        Board savedBoard = boardRepository.save(board);

        Likelist likelist = new Likelist();
        savedBoard.addBoardtoLikelist(likelist);
        savedUser.addUsertoLikelist(likelist);

        // when
        Likelist savedLikelist = likesRepository.save(likelist);

        // then
        Assertions.assertEquals(savedUser.getId(), savedLikelist.getUser().getId());
        Assertions.assertEquals(savedBoard.getId(), savedLikelist.getBoard().getId());
    }

    @Test
    @DisplayName("좋아요 취소")
    void undoLike() {
        //given
        User user = User.builder()
                .username("username")
                .email("na@gmail.com")
                .password("12333")
                .build();

        Board board = Board.builder()
                .grid("1234")
                .content("이름이 뭐니?")
                .username("helloworld")
                .imageUrl("image.com")
                .build();

        User savedUser = userRepository.save(user);
        Board savedBoard = boardRepository.save(board);

        Likelist likelist = new Likelist();
        savedBoard.addBoardtoLikelist(likelist);
        savedUser.addUsertoLikelist(likelist);

        LikesRequestDto dto = new LikesRequestDto(savedUser.getId());
        likesRepository.save(likelist);
        System.out.println(likesRepository.findAll().size());

        // when
        likesService.deletelikes(savedBoard.getId(), dto);
        System.out.println(likesRepository.findAll().size());

        // then
//        Assertions.assertNull(deletedlike);
        Assertions.assertEquals(0, likesRepository.findAll().size());
    }
}
