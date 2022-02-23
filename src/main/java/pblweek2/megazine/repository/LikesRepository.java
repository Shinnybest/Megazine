package pblweek2.megazine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pblweek2.megazine.domain.Likelist;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likelist, Long> {
    Optional<Likelist> findByBoard_IdAndUserId(Long boardId, Long userId);
}
