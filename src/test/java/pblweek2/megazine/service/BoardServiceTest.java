package pblweek2.megazine.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.UserRepository;
import java.util.Optional;


class BoardServiceTest {

    @Nested
    @DisplayName("새로운 게시글 생성")
    class postBoard {
        // given
        @Autowired
        UserRepository userRepository;

        @Autowired
        BoardRepository boardRepository;

//        private String grid;
//        private String content;
//        private String username;
//        private String imageUrl;



//        @BeforeEach
//        void setup() {
////            User user = User.builder()
////                    .username("어린왕자")
////                    .email("prince@naver.com")
////                    .password("little")
////                    .build();
//
////            userRepository.save(user);
//
////            BoardRequestDto requestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "어린왕자", "imageurl");
////
////            Board board = Board.builder()
////                    .user(user)
////                    .grid(requestDto.getGrid())
////                    .content(requestDto.getContent())
////                    .username(requestDto.getUsername())
////                    .imageUrl(requestDto.getImageUrl())
////                    .build();
////            Board board2 = Board.builder()
////                    .user(user)
////                    .grid("11")
////                    .content("나랑 놀아줘")
////                    .imageUrl("1111")
////                    .build();
////            Board board3 = Board.builder()
////                    .user(user)
////                    .grid("11")
////                    .content("오늘 심심해")
////                    .imageUrl("1111")
////                    .build();
////
////            Board savedBoard = boardRepository.save(board);
////            Board savedBoard1 = boardRepository.save(board2);
////            Board savedBoard2 = boardRepository.save(board3);
////
////
////            Optional<User> findUser = userRepository.findByEmail("prince@naver.com");
////            if(!findUser.isPresent()) {throw new NullPointerException();};
////            Likelist likes1 = Likelist.builder()
////                    .user(findUser.get())
////                    .board(board)
////                    .build();
////
////            Likelist likes2 = Likelist.builder()
////                    .user(findUser.get())
////                    .board(board2)
////                    .build();
////
////            Likelist likes3 = Likelist.builder()
////                    .user(findUser.get())
////                    .board(board3)
////                    .build();
////        }



        @AfterEach
        public void aftertest() {
            userRepository.deleteAll();
            boardRepository.deleteAll();
        }

        @Test
        @DisplayName("정상케이스")
        void createBoard_normal() {
            //given
            User user = User.builder()
                    .username("어린왕자")
                    .email("prince@naver.com")
                    .password("little")
                    .build();

            userRepository.save(user);


            BoardRequestDto requestDto = new BoardRequestDto("1", "이러쿵 저러쿵", "어린왕자", "imageurl");

            //when
            Board board = Board.builder()
                    .grid(requestDto.getGrid())
                    .content(requestDto.getContent())
                    .username(requestDto.getUsername())
                    .imageUrl(requestDto.getImageUrl())
                    .build();
            user.addUsertoBoard(board);

            Board savedBoard = boardRepository.save(board);
            Optional<Board> foundBoard = boardRepository.findById(savedBoard.getId());
            if (!foundBoard.isPresent()) { throw new NullPointerException(); };

            //then
                org.assertj.core.api.Assertions.assertThat(board.getContent()).isEqualTo(savedBoard.getContent());
                org.assertj.core.api.Assertions.assertThat(board.getUsername()).isEqualTo(savedBoard.getUsername());
                org.assertj.core.api.Assertions.assertThat(board.getGrid()).isEqualTo(savedBoard.getGrid());
                org.assertj.core.api.Assertions.assertThat(board.getImageUrl()).isEqualTo(savedBoard.getImageUrl());
                org.assertj.core.api.Assertions.assertThat(savedBoard.getId()).isEqualTo(foundBoard.get().getId());

        }

//        @Nested
//        @DisplayName("실패케이스")
//        class FailCases {
//            @Nested
//            @DisplayName("유저명")
//            class username {
//                @Test
//                @DisplayName("세자리 미만")
//                void fail1() {
//                    //given
//                    username = "ab";
//                    String passwordCheck = password;
//                    SignupRequestDto signupRequestDto = new SignupRequestDto(
//                            username,
//                            email,
//                            password,
//                            passwordCheck
//                    );
//
//
//
//                }
//            }
//        }

    }


}