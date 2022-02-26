package pblweek2.megazine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pblweek2.megazine.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.refresh_token = :refresh_token where u.email = :email")
    void updateRefreshToken(@Param(value = "email") String email, @Param(value = "refresh_token") String refresh_token);
}
