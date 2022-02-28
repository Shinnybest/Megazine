package pblweek2.megazine.mockobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.exception_2.CustomException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static pblweek2.megazine.exception_2.ErrorCode.EMAIL_NOT_FOUND;

public class MockBoardServiceTest {

    private MockBoardService mockBoardService;

    @AfterEach
        public void aftertest() {
            MockUserRepository mockUserRepository = new MockUserRepository();
            mockUserRepository.deleteAll();
            MockBoardRepository mockBoardRepository = new MockBoardRepository();
            mockBoardRepository.deleteAll();
        }

        @Test
        @DisplayName("새로운 게시글 생성(성공)")
        void postBoard_Normal() {
// given
            BoardRequestDto boardRequestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "어린왕자", "imageurl");

            User user = User.builder()
                    .username("어린왕자")
                    .email("prince@naver.com")
                    .password("little")
                    .build();

            MockUserRepository mockUserRepository = new MockUserRepository();

            User savedUser = mockUserRepository.save(user);

//            MockBoardService mockBoardService = new MockBoardService(mockUserRepository);
//            when(mockUserRepository.findByUsername(boardRequestDto.getUsername())
//                            .thenReturn(Optional.of(savedUser));

            Optional<User> foundUser = mockUserRepository.findByUsername(boardRequestDto.getUsername());
            if(!foundUser.isPresent()) {
                throw new CustomException(EMAIL_NOT_FOUND);
            }

// when
            Board board = mockBoardService.postBoard(boardRequestDto);
// then
            Assertions.assertThat(boardRequestDto.getContent()).isEqualTo(board.getContent());
            Assertions.assertThat(boardRequestDto.getGrid()).isEqualTo(board.getGrid());
            Assertions.assertThat(boardRequestDto.getUsername()).isEqualTo(board.getUsername());
            Assertions.assertThat(boardRequestDto.getImageUrl()).isEqualTo(board.getImageUrl());
            Assertions.assertThat(savedUser.getId()).isEqualTo(board.getUser().getId());

        }

        @Test
        @DisplayName("새로운 게시글 생성(실패)")
        void postBoard_Fail() {
        // given
            BoardRequestDto boardRequestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "나이든왕자", "imageurl");

            User user = User.builder()
                    .username("어린왕자")
                    .email("prince@naver.com")
                    .password("little")
                    .build();

            MockUserRepository mockUserRepository = new MockUserRepository();

            mockUserRepository.save(user);

            MockBoardService mockBoardService = new MockBoardService();
// when
            Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
                mockBoardService.postBoard(boardRequestDto);
            });
// then
            org.junit.jupiter.api.Assertions.assertEquals("존재하지 않는 사용자입니다.", exception.getMessage());
        }

    @Test
    @DisplayName("전체 게시글 조회(성공)")
    void readAllBoards_Normal() {
// given
        User user = User.builder()
                .username("어린왕자")
                .email("prince@naver.com")
                .password("little")
                .build();

        MockUserRepository mockUserRepository = new MockUserRepository();
        mockUserRepository.save(user);


        Board board = Board.builder()
                    .user(user)
                    .grid("11")
                    .content("22")
                    .username("어린왕자")
                    .imageUrl("2222")
                    .build();
        Board board1 = Board.builder()
                .user(user)
                .grid("11")
                .content("22")
                .username("어린왕자")
                .imageUrl("2222")
                .build();
        Board board2 = Board.builder()
                .user(user)
                .grid("11")
                .content("22")
                .username("어린왕자")
                .imageUrl("2222")
                .build();

        MockBoardRepository mockBoardRepository = new MockBoardRepository();
        mockBoardRepository.save(board);
        mockBoardRepository.save(board1);
        mockBoardRepository.save(board2);

        MockBoardService mockBoardService = new MockBoardService();
// when
        List<BoardResponseDto> boardResponseDtoList = mockBoardService.getAllBoardData();
// then
        Assertions.assertThat(3).isEqualTo(mockBoardRepository.findAll().size());
    }

    @Test
    @DisplayName("상세 게시글 조회(성공)")
    void readOneBoard_Normal() {
        // given
        User user = User.builder()
                .username("어린왕자")
                .email("prince@naver.com")
                .password("little")
                .build();

        MockUserRepository mockUserRepository = new MockUserRepository();
        User savedUser = mockUserRepository.save(user);

        Board board = Board.builder()
                .user(user)
                .grid("11")
                .content("22")
                .username("어린왕자")
                .imageUrl("2222")
                .build();
        MockBoardRepository mockBoardRepository = new MockBoardRepository();
        Board savedBoard = mockBoardRepository.save(board);

        MockBoardService mockBoardService = new MockBoardService();
        // when
        BoardResponseDto dto = mockBoardService.getOneBoardData(savedBoard.getId());
        // then
        Assertions.assertThat(dto.getContent()).isEqualTo(savedBoard.getContent());
        Assertions.assertThat(dto.getGrid()).isEqualTo(savedBoard.getGrid());
        Assertions.assertThat(dto.getCreater()).isEqualTo(savedBoard.getUsername());
        Assertions.assertThat(dto.getImageUrl()).isEqualTo(savedBoard.getImageUrl());
        Assertions.assertThat(dto.getBoardId()).isEqualTo(savedBoard.getId());
    }

    @Test
    @DisplayName("상세 게시글 조회(실패)")
    void readOneBoard_Fail() {
        // given
        User user = User.builder()
                .username("어린왕자")
                .email("prince@naver.com")
                .password("little")
                .build();

        MockUserRepository mockUserRepository = new MockUserRepository();

        mockUserRepository.save(user);

        Board board = Board.builder()
                .user(user)
                .grid("11")
                .content("22")
                .username("어린왕자")
                .imageUrl("2222")
                .build();
        MockBoardRepository mockBoardRepository = new MockBoardRepository();
        mockBoardRepository.save(board);

        MockBoardService mockBoardService = new MockBoardService();
// when
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
            mockBoardService.getOneBoardData(200L);
        });
// then
        org.junit.jupiter.api.Assertions.assertEquals("존재하지 않는 게시글입니다.", exception.getMessage());
    }

//    @Test
//    @DisplayName("게시글 수정(성공)")
//    void updateBoard_Normal() {
//// given
//        User user = User.builder()
//                .username("어린왕자")
//                .email("prince@naver.com")
//                .password("little")
//                .build();
//
//        MockUserRepository mockUserRepository = new MockUserRepository();
//        User savedUser = mockUserRepository.save(user);
//
//        Board board = Board.builder()
//                .user(user)
//                .grid("11")
//                .content("22")
//                .username("어린왕자")
//                .imageUrl("2222")
//                .build();
//        MockBoardRepository mockBoardRepository = new MockBoardRepository();
//        Board savedBoard = mockBoardRepository.save(board);
//
//        MockBoardService mockBoardService = new MockBoardService();
//// when
//        Board board = mockBoardService.update(savedBoard.getId());
//// then
//        Assertions.assertThat(boardRequestDto.getContent()).isEqualTo(board.getContent());
//        Assertions.assertThat(boardRequestDto.getGrid()).isEqualTo(board.getGrid());
//        Assertions.assertThat(boardRequestDto.getUsername()).isEqualTo(board.getUsername());
//        Assertions.assertThat(boardRequestDto.getImageUrl()).isEqualTo(board.getImageUrl());
//        Assertions.assertThat(savedUser.getId()).isEqualTo(board.getUser().getId());
//
//    }
//
//    @Test
//    @DisplayName("게시글 수정(실패1-보드 존재 x)")
//    void updateBoard_Fail1() {
//        // given
//        BoardRequestDto boardRequestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "나이든왕자", "imageurl");
//
//        User user = User.builder()
//                .username("어린왕자")
//                .email("prince@naver.com")
//                .password("little")
//                .build();
//
//        MockUserRepository mockUserRepository = new MockUserRepository();
//
//        mockUserRepository.save(user);
//
//        MockBoardService mockBoardService = new MockBoardService(mockUserRepository);
//// when
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
//            mockBoardService.postBoard(boardRequestDto);
//        });
//// then
//        org.junit.jupiter.api.Assertions.assertEquals("존재하지 않는 사용자입니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("게시글 수정(실패2-작성자 != 본인)")
//    void updateBoard_Fail2() {
//        // given
//        BoardRequestDto boardRequestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "나이든왕자", "imageurl");
//
//        User user = User.builder()
//                .username("어린왕자")
//                .email("prince@naver.com")
//                .password("little")
//                .build();
//
//        MockUserRepository mockUserRepository = new MockUserRepository();
//
//        mockUserRepository.save(user);
//
//        MockBoardService mockBoardService = new MockBoardService(mockUserRepository);
//// when
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
//            mockBoardService.postBoard(boardRequestDto);
//        });
//// then
//        org.junit.jupiter.api.Assertions.assertEquals("존재하지 않는 사용자입니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("게시글 삭제(성공)")
//    void deleteBoard_Normal() {
//// given
//        BoardRequestDto boardRequestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "어린왕자", "imageurl");
//
//        User user = User.builder()
//                .username("어린왕자")
//                .email("prince@naver.com")
//                .password("little")
//                .build();
//
//        MockUserRepository mockUserRepository = new MockUserRepository();
//
//        User savedUser = mockUserRepository.save(user);
//
//        MockBoardService mockBoardService = new MockBoardService(mockUserRepository);
//
//        Optional<User> foundUser = mockUserRepository.findByUsername(boardRequestDto.getUsername());
//        if(!foundUser.isPresent()) {
//            throw new CustomException(EMAIL_NOT_FOUND);
//        }
//
//// when
//        Board board = mockBoardService.postBoard(boardRequestDto);
//// then
//        Assertions.assertThat(boardRequestDto.getContent()).isEqualTo(board.getContent());
//        Assertions.assertThat(boardRequestDto.getGrid()).isEqualTo(board.getGrid());
//        Assertions.assertThat(boardRequestDto.getUsername()).isEqualTo(board.getUsername());
//        Assertions.assertThat(boardRequestDto.getImageUrl()).isEqualTo(board.getImageUrl());
//        Assertions.assertThat(savedUser.getId()).isEqualTo(board.getUser().getId());
//
//    }
//
//    @Test
//    @DisplayName("게시글 삭제(실패1-보드 존재 x)")
//    void deleteBoard_Fail1() {
//        // given
//        BoardRequestDto boardRequestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "나이든왕자", "imageurl");
//
//        User user = User.builder()
//                .username("어린왕자")
//                .email("prince@naver.com")
//                .password("little")
//                .build();
//
//        MockUserRepository mockUserRepository = new MockUserRepository();
//
//        mockUserRepository.save(user);
//
//        MockBoardService mockBoardService = new MockBoardService(mockUserRepository);
//// when
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
//            mockBoardService.postBoard(boardRequestDto);
//        });
//// then
//        org.junit.jupiter.api.Assertions.assertEquals("존재하지 않는 사용자입니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("게시글 삭제(실패2-작성자 != 본인)")
//    void deleteBoard_Fail2() {
//        // given
//        BoardRequestDto boardRequestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "나이든왕자", "imageurl");
//
//        User user = User.builder()
//                .username("어린왕자")
//                .email("prince@naver.com")
//                .password("little")
//                .build();
//
//        MockUserRepository mockUserRepository = new MockUserRepository();
//
//        mockUserRepository.save(user);
//
//        MockBoardService mockBoardService = new MockBoardService(mockUserRepository);
//// when
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
//            mockBoardService.postBoard(boardRequestDto);
//        });
//// then
//        org.junit.jupiter.api.Assertions.assertEquals("존재하지 않는 사용자입니다.", exception.getMessage());
//    }
}
