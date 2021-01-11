package social.parking.app.repository;

import social.parking.app.model.ReportEvent;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import social.parking.app.dto.EventsPie;


public interface ReportEventRepository extends JpaRepository<ReportEvent,Long> {

  
    @Query(value = "SELECT " +  "new social.parking.app.dto.EventsPie(r.type, COUNT(r.type)) "+" from ReportEvent r where r.type like %:type% ")
	public EventsPie getPieElementbyType(@Param("type")  String type); 

	@Query(value = "SELECT " + " new social.parking.app.dto.EventsPie(p.status, COUNT(r.type)) " + " from ReportEvent r inner join ParkingSpot p  on r.parkingSpot.spotId = p.spotId where r.type like 'Parking Place' and p.status like %:status% ")
	EventsPie getParkingPlaceByStatus(@Param("status") String status);
} 