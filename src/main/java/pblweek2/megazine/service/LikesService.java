package pblweek2.megazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.Likelist;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.LikesRequestDto;
import pblweek2.megazine.exception.CustomException;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.LikesRepository;
import pblweek2.megazine.repository.UserRepository;

import java.util.Optional;

import static pblweek2.megazine.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public Likelist addlikes(Long boardId, LikesRequestDto likesrequestDto) {
        if (likesRepository.findByBoard_IdAndUserId(boardId, likesrequestDto.getUserId()).isPresent()) {
            throw new CustomException(ALREADY_LIKED_BOARD);
        }
        Optional<Board> board = Optional.ofNullable(boardRepository.findById(boardId))
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
        Optional<User> user = Optional.ofNullable(userRepository.findById(likesrequestDto.getUserId()))
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
        Likelist likelist = new Likelist();
        board.get().addBoardtoLikelist(likelist);
        user.get().addUsertoLikelist(likelist);
        return likelist;
    }

    @Transactional
    public void deletelikes(Long boardId, LikesRequestDto likesRequestDto) {
        Optional<Likelist> likelist = Optional.ofNullable(likesRepository.findByBoard_IdAndUserId(boardId, likesRequestDto.getUserId()))
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
        likesRepository.delete(likelist.get());
    }
}
