package pblweek2.megazine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pblweek2.megazine.domain.Likelist;
import pblweek2.megazine.dto.LikesRequestDto;
import pblweek2.megazine.entityResponse.CancelLikeBoard;
import pblweek2.megazine.entityResponse.LikeBoard;
import pblweek2.megazine.repository.LikesRepository;
import pblweek2.megazine.service.LikesService;

@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;
    private final LikesRepository likesRepository;


    @PostMapping("/api/board/{boardId}/like")
    public ResponseEntity<LikeBoard> likeBoard(@PathVariable Long boardId, @RequestBody LikesRequestDto likesrequestDto) {
            Likelist likelist = likesService.addlikes(boardId, likesrequestDto);
            likesRepository.save(likelist);
            return new ResponseEntity<>(new LikeBoard("success", "좋아요 추가 성공"), HttpStatus.OK);
    }

    @DeleteMapping("/api/board/{boardId}/like")
    public ResponseEntity<CancelLikeBoard> cancelLikeBoard(@PathVariable Long boardId, @RequestBody LikesRequestDto likesRequestDto) {
        likesService.deletelikes(boardId, likesRequestDto);
        return new ResponseEntity<>(new CancelLikeBoard("success", "좋아요 삭제 성공"), HttpStatus.OK);
    }
}