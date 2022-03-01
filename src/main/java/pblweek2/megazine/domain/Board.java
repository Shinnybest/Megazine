package pblweek2.megazine.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import pblweek2.megazine.dto.BoardChangeRequestDto;
import pblweek2.megazine.dto.BoardRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Board extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String grid;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likelist> likelist = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto) {
        this.grid = boardRequestDto.getGrid();
        this.content = boardRequestDto.getContent();
        this.username = boardRequestDto.getUsername();
        this.imageUrl = boardRequestDto.getImageUrl();
    }

    @Builder
    public Board(String grid, String content, String username, String imageUrl, User user) {
        Assert.hasText(grid, "grid must not be empty.");
        Assert.hasText(content, "content must not be empty.");
        Assert.hasText(username, "username must not be empty.");
        Assert.hasText(imageUrl, "imageUrl must not be empty.");

        this.grid = grid;
        this.content = content;
        this.username = username;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public void setUser(User user) { this.user = user; }

    public void setId(Long id) { this.id = id; }

    public void addBoardtoLikelist(Likelist likelist) { likelist.setBoard(this); }

    public void update(BoardRequestDto requestDto) {
        this.grid = requestDto.getGrid();
        this.content = requestDto.getContent();
        this.username = requestDto.getUsername();
        this.imageUrl = requestDto.getImageUrl();
    }

}
