package social.parking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import social.parking.app.model.RefreshToken;

import java.util.Optional;
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
