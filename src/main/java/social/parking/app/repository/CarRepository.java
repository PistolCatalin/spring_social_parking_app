package social.parking.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import social.parking.app.model.User;
import social.parking.app.model.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
    Optional<Car> findByUser(User user);
} 
