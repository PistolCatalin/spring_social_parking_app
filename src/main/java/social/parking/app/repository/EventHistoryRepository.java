package social.parking.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import social.parking.app.model.EventHistory;
public interface EventHistoryRepository extends JpaRepository<EventHistory,Long> {

	List<EventHistory> findAllByOrderByIdDesc();
	

}
