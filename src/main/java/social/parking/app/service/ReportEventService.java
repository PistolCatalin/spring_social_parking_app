package social.parking.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import social.parking.app.dto.EventsPie;
import social.parking.app.dto.ReportEventRequest;
import social.parking.app.mapper.ReportEventMapper;
import social.parking.app.model.EventHistory;
import social.parking.app.model.ParkingSpot;
import social.parking.app.model.ReportEvent;
import social.parking.app.repository.EventHistoryRepository;
import social.parking.app.repository.ParkingSpotRepository;
import social.parking.app.repository.ReportEventRepository;
import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.text.SimpleDateFormat;
@Service
@AllArgsConstructor
@Slf4j

public class ReportEventService {

    private final ReportEventMapper reportEventMapper;
    private final ReportEventRepository reportEventRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final EventHistoryRepository eventHistoryRepository;
    private final AuthService authService;

    @Transactional
    public void add (ReportEventRequest reportEventRequest){
        
    
        String event_verbose = reportEventRequest.getType().equalsIgnoreCase("Parking Place") ? "add a \u0000" : "report an \u0000";
        String status = reportEventRequest.getType().equalsIgnoreCase("Parking Place")  ? "Free " : "Not available ";
        //Add Parking Spot;
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setPrice(reportEventRequest.getPrice());
        parkingSpot.setJsonLocation(reportEventRequest.getLatitude(),reportEventRequest.getLongitutde());
        parkingSpot.setStatus(status);
        parkingSpot = parkingSpotRepository.save(parkingSpot);
        ReportEvent reportEvent = reportEventMapper.map(reportEventRequest, parkingSpot);
      

        
        //Generate history
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");
        EventHistory eventHistory = new EventHistory();
        String dtStr = formatter.format(date);

        eventHistory.setSubject(event_verbose + reportEventRequest.getType());
        eventHistory.setTimestamp(dtStr);
        eventHistory.setUsername(authService.getCurrentUser().getUsername());
        eventHistory.setLatitude(reportEventRequest.getLatitude());
        eventHistory.setLongitutde(reportEventRequest.getLongitutde());
        eventHistory.setStatus(status);
        eventHistoryRepository.save(eventHistory);
        reportEventRepository.save(reportEvent); 
    }

    public void reserveParkingSpot (Long id,Long minutes){

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");
     
        EventHistory eventHistory = new EventHistory();

        Optional<ReportEvent> reportEvent = reportEventRepository.findById(id);
        ParkingSpot parkingSpot = reportEvent.map(ReportEvent::getParkingSpot).orElse(null);
        parkingSpot.setExpiresAt(Instant.now().plus(minutes,ChronoUnit.MINUTES));
        parkingSpot.setStatus("Reserved");
        System.out.println(parkingSpot + "\n");
        ReportEvent reportEvent1 = reportEvent.get();
        
        reportEvent1.setParkingSpot(parkingSpot);

        reportEventRepository.save(reportEvent1);
        //Generate history
            //BUG PARSE LOCATIONNNNNNNN

        Date until = new Date();
        Date now = new Date();
		until= Date.from(Instant.now().plus(minutes,ChronoUnit.MINUTES));
        eventHistory.setSubject("has reserved a parking place");
        eventHistory.setTimestamp(formatter.format(now) + "-" + formatter.format(until) );
        eventHistory.setUsername(authService.getCurrentUser().getUsername());
        eventHistory.setLatitude(parseLocation(parkingSpot.getLocation(),"latitude"));
        eventHistory.setLongitutde(parseLocation(parkingSpot.getLocation(),"longitude"));
        eventHistory.setStatus("Reserved");
        eventHistoryRepository.save(eventHistory);
        
    }

    public List<EventHistory> getAllEventHistory(){
        log.info("getEmployeeName starts");
        return eventHistoryRepository.findAllByOrderByIdDesc();
    }

    public List<ReportEventRequest> getAllEvents(){
        return reportEventRepository.findAll().stream().map(reportEventMapper::mapToDto).collect(toList());
    }
    String parseLocation(String location,String key) {
       
        JSONObject json = new JSONObject(location);
        return (String) json.get(key);
    }
    public List<EventsPie> getAllDataPie(){
       List<EventsPie> list =  new ArrayList<EventsPie>();

       list.add(new EventsPie("Illegal Parking",reportEventRepository.getPieElementbyType("Illegal Parking").getY()));
       list.add(new EventsPie("Abandoned vehicle",reportEventRepository.getPieElementbyType("Abandoned vehicle").getY()));
       list.add(new EventsPie("Reserved",reportEventRepository.getParkingPlaceByStatus("Reserved").getY()));
       list.add(new EventsPie("Free",reportEventRepository.getParkingPlaceByStatus("Free").getY()));



    //    list.add(reportEventRepository.getPieElementbyType("Illegal Parking"));
    //    list.add(reportEventRepository.getPieElementbyType("Abandoned vehicle"));
       return list;
    }
    @Scheduled(fixedDelay = 20000)
    //@Scheduled(fixedRate = 5000)  //Or use this
    public void demoServiceMethod()
    {   
        List<ReportEvent> list_report_event = reportEventRepository.findAll();
        ParkingSpot parkingSpot;
        for (ReportEvent r : list_report_event){
            parkingSpot = r.getParkingSpot();
            if(parkingSpot.getExpiresAt() != null && parkingSpot.getExpiresAt().compareTo(Instant.now()) < 0  && parkingSpot.getStatus().equalsIgnoreCase("Reserved") ){
                //Generate history
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");
                EventHistory eventHistory= new EventHistory();
                String dtStr = formatter.format(date);

                eventHistory.setSubject(" carries a vacated parking space");
                eventHistory.setTimestamp(dtStr);
                eventHistory.setUsername("System ");
                eventHistory.setLatitude(parseLocation(parkingSpot.getLocation(), "latitude"));
                eventHistory.setLongitutde(parseLocation(parkingSpot.getLocation(), "longitude"));
                eventHistory.setStatus("Free");
                parkingSpot.setStatus("Free");
            
                parkingSpotRepository.save(parkingSpot);
                eventHistoryRepository.save(eventHistory);
                
                System.out.println("Parking Spot" +parkingSpot.getSpotId()+ "was updated\n");

            }
        }

        System.out.println("Updating status parking place finished. Current time is :: "+ new Date());
    }

}
