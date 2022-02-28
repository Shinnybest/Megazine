package pblweek2.megazine.mockobject;

import pblweek2.megazine.domain.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockBoardRepository {
        private List<Board> boards = new ArrayList<>();
        // 게시판 테이블 ID: 1부터 시작
        private Long boardId = 1L;

        // 게시판 저장
        public Board save(Board board) {
            // 신규 상품 -> DB 에 저장
            board.setId(boardId);
            ++boardId;
            boards.add(board);
            return board;
        }

        // 상품 ID 로 상품 조회
        public Optional<Board> findById(Long id) {
            for (Board board : boards) {
                if (board.getId().equals(id)) {
                    return Optional.of(board);
                }
            }
            return Optional.empty();
        }

        // 전체 게시글 조회
        public List<Board> findAll() {
            return boards;
        }

        // 게시글 삭제
        public void delete(Board board) {
            for (Board each : boards) {
                if (each.getId().equals(board.getId())) {
                    boards.remove(each);
                }
            }
        }

        public void deleteAll() {
            boards.clear();
        }
    }
