package pblweek2.megazine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pblweek2.megazine.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
