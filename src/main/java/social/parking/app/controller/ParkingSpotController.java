package social.parking.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.ResponseEntity;

import social.parking.app.dto.ParkingSpotBody;
import social.parking.app.model.ParkingSpot;
import social.parking.app.service.parkingSportService;

@RestController
@RequestMapping("/api/parkingSpot/")
@AllArgsConstructor
public class ParkingSpotController {
    private final parkingSportService parkingSportService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody ParkingSpotBody parkingSpotBody) {
        parkingSportService.save(parkingSpotBody);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> getAllParkingSpots() {
        parkingSportService.getAllParkingSport();
        return status(OK).body(parkingSportService.getAllParkingSport());
    }
}
