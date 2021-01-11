package social.parking.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import social.parking.app.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
