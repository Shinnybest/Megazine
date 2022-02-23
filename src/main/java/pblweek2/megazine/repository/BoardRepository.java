package pblweek2.megazine.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pblweek2.megazine.domain.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
