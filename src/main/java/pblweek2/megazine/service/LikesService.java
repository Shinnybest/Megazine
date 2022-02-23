package pblweek2.megazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import pblweek2.megazine.domain.Board;
import pblweek2.megazine.domain.Likelist;
import pblweek2.megazine.domain.User;
import pblweek2.megazine.dto.LikesRequestDto;
import pblweek2.megazine.exception.UserNotLoginException;
import pblweek2.megazine.repository.BoardRepository;
import pblweek2.megazine.repository.LikesRepository;
import pblweek2.megazine.repository.UserRepository;
import pblweek2.megazine.security.UserDetailsImpl;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;


    public Likelist postLikes(Long boardId, LikesRequestDto likesRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        User user = userRepository.findById(likesRequestDto.getUserId()).orElseThrow(NullPointerException::new);
        Likelist likelist = new Likelist();
        likelist.setBoard(board);
        likelist.setUser(user);
        return likelist;
    }

    public boolean checkUserExists(LikesRequestDto likesrequestDto) {
        if (userRepository.findById(likesrequestDto.getUserId()) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Likelist addlikes(Long boardId, LikesRequestDto likesrequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null){
            throw new UserNotLoginException();
        }
        Optional<Board> board = Optional.ofNullable(boardRepository.findById(boardId)).orElseThrow(NullPointerException::new);
        Optional<User> user = Optional.ofNullable(userRepository.findById(likesrequestDto.getUserId())).orElseThrow(NullPointerException::new);
        Likelist likelist = new Likelist();
        board.get().addBoardtoLikelist(likelist);
        user.get().addUsertoLikelist(likelist);
        return likelist;
    }

    public Likelist deletelikes(Long boardId, LikesRequestDto likesRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null) {
            throw new UserNotLoginException();
        }

        Optional<Likelist> likelist = Optional.ofNullable(likesRepository.findByBoard_IdAndUserId(boardId, likesRequestDto.getUserId())).orElseThrow(NullPointerException::new);
        return likelist.get();
    }
}
