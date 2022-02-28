package pblweek2.megazine.mockobject;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.BoardDeleteRequestDto;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.exception_2.CustomException;
import pblweek2.megazine.security.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pblweek2.megazine.exception_2.ErrorCode.*;

public class MockBoardService {
        private final MockBoardRepository mockBoardRepository;
        private final MockUserRepository mockUserRepository;

        public MockBoardService() {
            this.mockBoardRepository = new MockBoardRepository();
            this.mockUserRepository = new MockUserRepository();
        }

        public Board postBoard(BoardRequestDto boardRequestDto) {
            Board board = new Board(boardRequestDto);
            Optional<User> user = mockUserRepository.findByUsername(boardRequestDto.getUsername());
            if(!user.isPresent()) {
                throw new CustomException(EMAIL_NOT_FOUND);
            }
            User foundUser = user.get();
            foundUser.addUsertoBoard(board);
            mockBoardRepository.save(board);
            return board;
        }

        public List<BoardResponseDto> getAllBoardData() {
            List<Board> board = mockBoardRepository.findAll();
            List<BoardResponseDto> boardResponseDtoList = new ArrayList<BoardResponseDto>();
            for (Board each : board) {
                BoardResponseDto boardResponseDto = new BoardResponseDto(each);
                boardResponseDtoList.add(boardResponseDto);
            }
            return boardResponseDtoList;
        }

        public BoardResponseDto getOneBoardData(Long boardId) {
            Optional<Board> foundBoard = mockBoardRepository.findById(boardId);
            if (!foundBoard.isPresent()) {
                throw new CustomException(BOARD_NOT_FOUND);
            }
            BoardResponseDto boardResponseDto = new BoardResponseDto(foundBoard.get());
            return boardResponseDto;
        }

        @Transactional
        public void update(Long boardId, BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            Optional<Board> board = mockBoardRepository.findById(boardId);
            if (!board.isPresent()) {
                throw new CustomException(BOARD_NOT_FOUND);
            }
            if (userDetails.getUsername().equals(requestDto.getUsername())) {
                Board foundBoard = board.get();
                foundBoard.update(requestDto);
            } else {
                throw new CustomException(UNABLE_UPDATE_BOARD);
            }
        }

        public void delete(Long boardId, BoardDeleteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            Optional<Board> board = mockBoardRepository.findById(boardId);
            if (!board.isPresent()) {
                throw new CustomException(BOARD_NOT_FOUND);
            }
            if (userDetails.getUsername().equals(requestDto.getUsername())) {
                Board foundBoard = board.get();
                mockBoardRepository.delete(foundBoard);
            } else {
                throw new CustomException(UNABLE_DELETE_BOARD);
            }


        }


}
