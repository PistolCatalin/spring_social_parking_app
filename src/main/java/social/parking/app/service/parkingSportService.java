package social.parking.app.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import social.parking.app.dto.ParkingSpotBody;
import social.parking.app.model.ParkingSpot;
import social.parking.app.repository.ParkingSpotRepository;

@Service

@Slf4j

@AllArgsConstructor
public class parkingSportService {
    private final ParkingSpotRepository parkingSpotRepository;
    
    public void save(ParkingSpotBody parkingSpotBody) {
        ParkingSpot parkingSpot = new ParkingSpot();
        

        JSONObject location1 = new JSONObject();
        location1.put("longitude", parkingSpotBody.getLongitutde());
        location1.put("latitude", parkingSpotBody.getLatitude());

        parkingSpot.setLocation(location1.toString());
        parkingSpot.setPrice(parkingSpotBody.getPrice());
        parkingSpot.setStatus(parkingSpotBody.getStatus());
        parkingSpot.setReserved_time(parkingSpotBody.getReserved_time());
        parkingSpotRepository.save(parkingSpot);
    }

    public List<ParkingSpot> getAllParkingSport(){
        return parkingSpotRepository.findAll();
       // System.out.println(parkingSpotRepository.findAll());
    }
}
