package social.parking.app.mapper;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import javax.annotation.Generated;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import social.parking.app.dto.ReportEventRequest;
import social.parking.app.model.ParkingSpot;
import social.parking.app.model.ReportEvent;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-09T14:30:16+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9.1 (Ubuntu)"
)
@Component
@AllArgsConstructor
public class ReportEventMapper {

 
    public ReportEvent map(ReportEventRequest reportEvent, ParkingSpot parkingSpot) {
        if ( reportEvent == null && parkingSpot == null ) {
            return null;
        }

        ReportEvent reportEvent1 = new ReportEvent();

        if ( reportEvent != null ) {
            reportEvent1.setType( reportEvent.getType() );
            reportEvent1.setDescription( reportEvent.getDescription() );
        }
        if ( parkingSpot != null ) {
            reportEvent1.setParkingSpot( parkingSpot );
        }

        return reportEvent1;
    }

    public ReportEventRequest mapToDto(ReportEvent reportEvent) {
        if ( reportEvent == null ) {
            return null;
        }

        ReportEventRequest reportEventRequest = new ReportEventRequest();

        reportEventRequest.setId( reportEvent.getReportId() );
        reportEventRequest.setPrice( reportEventParkingSpotPrice( reportEvent ) );
        reportEventRequest.setStatus( reportEventParkingSpotStatus( reportEvent ) );
        reportEventRequest.setType( reportEvent.getType() );
        reportEventRequest.setDescription( reportEvent.getDescription() );

        reportEventRequest.setLongitutde( parseLocation(reportEvent.getParkingSpot().getLocation(),"longitude") );
        reportEventRequest.setLatitude( parseLocation(reportEvent.getParkingSpot().getLocation(),"latitude") );

        return reportEventRequest;
    }

    private Long reportEventParkingSpotPrice(ReportEvent reportEvent) {
        if ( reportEvent == null ) {
            return null;
        }
        ParkingSpot parkingSpot = reportEvent.getParkingSpot();
        if ( parkingSpot == null ) {
            return null;
        }
        Long price = parkingSpot.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }

    private String reportEventParkingSpotStatus(ReportEvent reportEvent) {
        if ( reportEvent == null ) {
            return null;
        }
        ParkingSpot parkingSpot = reportEvent.getParkingSpot();
        String status = parkingSpot.getStatus();
        
        if ( status == null ) {
            return null;
        }
        return status;
    }
    String parseLocation(String location,String key) {
       
        JSONObject json = new JSONObject(location);
        return (String) json.get(key);
    }
}
