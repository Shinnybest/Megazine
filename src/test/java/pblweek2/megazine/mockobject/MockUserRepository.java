package pblweek2.megazine.mockobject;

import pblweek2.megazine.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockUserRepository {
    private List<User> users = new ArrayList<>();
    // 게시판 테이블 ID: 1부터 시작
    private Long userId = 1L;

    public User save(User user) {
        user.setId(userId);
        ++userId;
        users.add(user);
        return user;

    }

    public Optional<User> findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void deleteAll() {
        users.clear();
    }
}
