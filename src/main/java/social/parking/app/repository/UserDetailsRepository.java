package social.parking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import social.parking.app.model.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
    
}
