package pblweek2.megazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.exception.BoardNotFoundException;
import pblweek2.megazine.exception.UnableToupdateBoardException;
import pblweek2.megazine.exception.UserNotLoginException;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.UserRepository;
import pblweek2.megazine.security.UserDetailsImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public Long postBoard(BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new UserNotLoginException();
        }
        Board board = new Board(boardRequestDto);
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(boardRequestDto.getUsername())).orElseThrow(NullPointerException::new);
        user.get().addUsertoBoard(board);
        boardRepository.save(board);
        return board.getId();
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

    public BoardResponseDto getOneBoardData(Long boardId_param, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Optional<Board> board = Optional.ofNullable(boardRepository.findById(boardId_param)).orElseThrow(BoardNotFoundException::new);
        Optional<Board> foundBoard = boardRepository.findById(boardId_param);
        if (foundBoard == null) {
            throw new BoardNotFoundException();
        }
        BoardResponseDto boardResponseDto = new BoardResponseDto(foundBoard.get());
        return boardResponseDto;
    }


    public void update(Long boardId, BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new UserNotLoginException();
        }
        Optional<Board> board = Optional.ofNullable(boardRepository.findById(boardId)).orElseThrow(NullPointerException::new);
        if (userDetails.getUsername().equals(requestDto.getUsername())) {
            board.get().update(requestDto);
        } else {
            throw new UnableToupdateBoardException();
        }
    }

    public void delete(Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new UserNotLoginException();
        }

        Optional<Board> board = Optional.ofNullable(boardRepository.findById(boardId)).orElseThrow(NullPointerException::new);
        boardRepository.delete(board.get());
    }
}
