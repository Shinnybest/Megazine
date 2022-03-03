package pblweek2.megazine.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pblweek2.megazine.dto.LikesRequestDto;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor
public class Likelist {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Builder
    public Likelist(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public void setUser(User user) { this.user = user; }
    public void setBoard(Board board) { this.board = board; }

}
