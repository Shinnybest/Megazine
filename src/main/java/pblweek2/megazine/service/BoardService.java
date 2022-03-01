package pblweek2.megazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.BoardDeleteRequestDto;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.exception_2.CustomException;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.UserRepository;
import pblweek2.megazine.security.UserDetailsImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pblweek2.megazine.exception_2.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board postBoard(BoardRequestDto boardRequestDto) {
            Board board = Board.builder()
                    .grid(boardRequestDto.getGrid())
                    .content(boardRequestDto.getContent())
                    .username(boardRequestDto.getUsername())
                    .imageUrl(boardRequestDto.getImageUrl())
                    .build();

            Optional<User> user = userRepository.findByUsername(board.getUsername());
            if(!user.isPresent()) {
                throw new CustomException(EMAIL_NOT_FOUND);
            }
            User foundUser = user.get();
            foundUser.addUsertoBoard(board);
            boardRepository.save(board);
            return board;
    }

    public List<BoardResponseDto> getAllBoardData() {
        List<Board> board = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<BoardResponseDto>();
        for (Board each : board) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(each);
            boardResponseDtoList.add(boardResponseDto);
        }
        return boardResponseDtoList;
    }

    public BoardResponseDto getOneBoardData(Long boardId) {
        Optional<Board> foundBoard = boardRepository.findById(boardId);
        if (!foundBoard.isPresent()) {
            throw new CustomException(BOARD_NOT_FOUND);
        }
        BoardResponseDto boardResponseDto = new BoardResponseDto(foundBoard.get());
        return boardResponseDto;
    }

    @Transactional
    public void update(Long boardId, BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Board> board = boardRepository.findById(boardId);
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
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new CustomException(BOARD_NOT_FOUND);
        }
        if (userDetails.getUsername().equals(requestDto.getUsername())) {
            Board foundBoard = board.get();
            boardRepository.delete(foundBoard);
        } else {
            throw new CustomException(UNABLE_DELETE_BOARD);
        }


    }
}
