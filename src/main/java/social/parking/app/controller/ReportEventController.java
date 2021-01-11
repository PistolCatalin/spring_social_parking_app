package social.parking.app.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import lombok.AllArgsConstructor;
import social.parking.app.dto.EventsPie;
import social.parking.app.dto.ReportEventRequest;
import social.parking.app.model.EventHistory;
import social.parking.app.service.ReportEventService;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;
import java.util.List;
@RestController
@RequestMapping("/api/event/")
@AllArgsConstructor
public class ReportEventController {
    
    private final ReportEventService reporteventService;
    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody ReportEventRequest reportEventRequest){
        reporteventService.add(reportEventRequest);
        return new ResponseEntity<>("Event was successfully created",
                OK);
    }
    @GetMapping
    public ResponseEntity<List<ReportEventRequest>>  getAllEvent(){
        
        return status(HttpStatus.OK).body(reporteventService.getAllEvents());
    }

    @GetMapping("/reserve/{id}/{minutes}")
    public ResponseEntity<String> reserveParkingSpot(@PathVariable Long id,@PathVariable Long minutes){
        reporteventService.reserveParkingSpot(id,minutes);
        return new ResponseEntity<>("The Parking Place was reserved",
                OK);
    }
    @GetMapping("/getPieChartEvents")
    public ResponseEntity<List<EventsPie>> getPieEvents() {
        return ResponseEntity.status(OK).body(reporteventService.getAllDataPie());
    }
    @GetMapping("/getHistory")
    public ResponseEntity<List<EventHistory>> reserveParkingSpot(){
        
        return  ResponseEntity.status(OK).body(reporteventService.getAllEventHistory());
    }

    
}
