package pblweek2.megazine.domain;

import lombok.Getter;
import pblweek2.megazine.dto.LikesRequestDto;

import javax.persistence.*;

@Getter
@Entity
public class Likelist {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public void setUser(User user) { this.user = user; }
    public void setBoard(Board board) { this.board = board; }

}
