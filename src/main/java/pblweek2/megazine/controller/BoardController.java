package pblweek2.megazine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pblweek2.megazine.dto.BoardChangeRequestDto;
import pblweek2.megazine.dto.BoardRequestDto;
import pblweek2.megazine.dto.BoardResponseDto;
import pblweek2.megazine.entityResponse.*;
import pblweek2.megazine.security.UserDetailsImpl;
import pblweek2.megazine.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity<AddBoard> addBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long board_id = boardService.postBoard(boardRequestDto, userDetails);
        return new ResponseEntity<>(new AddBoard("success", "게시물 생성되었습니다.", board_id), HttpStatus.OK);
    }

    @GetMapping("/api/board")
    @ResponseBody
    public ResponseEntity<ShowAllBoard> showAllBoard() {
        return new ResponseEntity<>(new ShowAllBoard("success", "전체 게시글을 불러왔습니다.", boardService.getAllBoardData()), HttpStatus.OK);
    }

    @GetMapping("/api/board/{boardId}")
    @ResponseBody
    public ResponseEntity<ReadOneBoard> readOneBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(new ReadOneBoard("success", "게시물을 불러왔습니다.", boardService.getOneBoardData(boardId, userDetails)), HttpStatus.OK);
    }

    @PutMapping("/api/board/{boardId}")
    public ResponseEntity<PutBoard> changeBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.update(boardId, requestDto, userDetails);
        return new ResponseEntity<>(new PutBoard("success", "수정완료 되었습니다."), HttpStatus.OK);

    }

    @DeleteMapping("/api/board/{boardId}")
    public ResponseEntity<DeleteBoard> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.delete(boardId, userDetails);
        return new ResponseEntity<>(new DeleteBoard("success", "삭제 완료되었습니다."), HttpStatus.OK);
    }
}
