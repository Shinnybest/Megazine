package pblweek2.megazine.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import javax.persistence.*;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class User extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Column
//    private String refresh_token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> board = new ArrayList<Board>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likelist> likelist = new ArrayList<Likelist>();

    @Builder
    public User(String username, String email, String password) {
        Assert.hasText(username, "username must not be empty.");
        Assert.hasText(email, "email must not be empty.");
        Assert.hasText(password, "password must not be empty.");

        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setId(Long id) { this.id = id; }

    public void addUsertoBoard(Board board) { board.setUser(this); }

    public void addUsertoLikelist(Likelist likelist) { likelist.setUser(this);}
}
