package pblweek2.megazine.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.exception.CustomException;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.UserRepository;
import pblweek2.megazine.service.BoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class BoardIntegrationTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardService boardService;

    @Test
    @Order(1)
    @DisplayName("전체 게시글 조회-성공")
    void readAllBoard() {
        //given
        Board board = Board.builder()
                .grid("1234")
                .content("이름이 뭐니?")
                .username("helloworld")
                .imageUrl("image.com")
                .build();

        Board board2 = Board.builder()
                .grid("5678")
                .content("즐겁군!")
                .username("summernight")
                .imageUrl("image.co.kr")
                .build();

        boardRepository.save(board);
        boardRepository.save(board2);

        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<BoardResponseDto>();

        //when
        for (Board each : boards) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(each);
            boardResponseDtoList.add(boardResponseDto);
        }

        //then
        Assertions.assertThat(boardResponseDtoList.size()).isEqualTo(boardRepository.findAll().size());
        Assertions.assertThat(boardResponseDtoList.indexOf(0)).isEqualTo(boardRepository.findAll().indexOf(0));
        Assertions.assertThat(boardResponseDtoList.indexOf(-1)).isEqualTo(boardRepository.findAll().indexOf(-1));
    }

    @Test
    @Order(2)
    @DisplayName("상세페이지 조회-성공")
    void readOneBoard() {
        //given
        Board board = Board.builder()
                .grid("1234")
                .content("이름이 뭐니?")
                .username("helloworld")
                .imageUrl("image.com")
                .build();


        Board savedBoard = boardRepository.save(board);

        Optional<Board> foundBoard = boardRepository.findById(board.getId());

        //when
        BoardResponseDto dto = new BoardResponseDto(foundBoard.get());

        //then
        Assertions.assertThat(dto.getContent()).isEqualTo(savedBoard.getContent());
        Assertions.assertThat(dto.getGrid()).isEqualTo(savedBoard.getGrid());
        Assertions.assertThat(dto.getCreater()).isEqualTo(savedBoard.getUsername());
        Assertions.assertThat(dto.getImageUrl()).isEqualTo(savedBoard.getImageUrl());
        Assertions.assertThat(dto.getBoardId()).isEqualTo(savedBoard.getId());
    }

    @Test
    @Order(3)
    @DisplayName("상세페이지 조회-실패")
    void fail_readOneBoard() {
        //given

        //when
        CustomException exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class,
                () -> boardService.getOneBoardData(5000L));

        //then
        org.junit.jupiter.api.Assertions.assertEquals("존재하지 않는 게시글입니다.", exception.getErrorCode().getErrorMessage());

    }

    @Test
    @Order(4)
    @DisplayName("게시글 업로드-성공")
    void postBoard() {
        //given
        BoardRequestDto dto = new BoardRequestDto("1234", "이름이 뭐니?", "love", "image.com");
        Board board = Board.builder()
                .grid(dto.getGrid())
                .content(dto.getContent())
                .username(dto.getUsername())
                .imageUrl(dto.getImageUrl())
                .build();
        Optional<User> user = userRepository.findByUsername(dto.getUsername());

        User foundUser = user.get();
        foundUser.addUsertoBoard(board);

        //when
        Board savedBoard = boardRepository.save(board);

        //then
        Assertions.assertThat(dto.getContent()).isEqualTo(savedBoard.getContent());
        Assertions.assertThat(dto.getGrid()).isEqualTo(savedBoard.getGrid());
        Assertions.assertThat(dto.getUsername()).isEqualTo(savedBoard.getUsername());
        Assertions.assertThat(dto.getImageUrl()).isEqualTo(savedBoard.getImageUrl());
    }

    @Test
    @Order(5)
    @DisplayName("게시글 수정-성공")
    void updateBoard() {
        //given
        Board board = Board.builder()
                .grid("1234")
                .content("이름이 뭐니?")
                .username("helloworld")
                .imageUrl("image.com")
                .build();
        Board savedBoard = boardRepository.save(board);
        BoardRequestDto dto = new BoardRequestDto("2345", "취미가 뭐니?", "helloworld", "www.naver.com");

        //when
        savedBoard.update(dto);
        Optional<Board> foundBoard = boardRepository.findById(savedBoard.getId());

        // then
        Assertions.assertThat(foundBoard.get().getUsername()).isEqualTo(savedBoard.getUsername());
        Assertions.assertThat(foundBoard.get().getContent()).isEqualTo(savedBoard.getContent());
        Assertions.assertThat(foundBoard.get().getGrid()).isEqualTo(savedBoard.getGrid());
        Assertions.assertThat(foundBoard.get().getImageUrl()).isEqualTo(savedBoard.getImageUrl());
    }

    @Test
    @Order(6)
    @DisplayName("게시글 삭제-성공")
    void deleteBoard() {
        //given
        Board board = Board.builder()
                .grid("1234")
                .content("반갑습니다!")
                .username("Nicetomeetyou")
                .imageUrl("image.com")
                .build();
        Board savedBoard = boardRepository.save(board);

        //when
        boardRepository.delete(savedBoard);

        //then
        Assertions.assertThat(0).isEqualTo(boardRepository.findAll().size());

    }
}
