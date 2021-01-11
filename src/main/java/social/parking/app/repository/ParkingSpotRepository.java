package social.parking.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import social.parking.app.model.ParkingSpot;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

} 
