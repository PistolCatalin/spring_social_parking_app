package social.parking.app.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long spotId;
    private String location;
    private Long price;
    private String status;
    private Long reserved_time;
    private Instant expiresAt;

    public void setJsonLocation(String latitute, String longitutde){
        JSONObject location1 = new JSONObject();
        location1.put("latitude", latitute);
        location1.put("longitude", longitutde);

        this.setLocation(location1.toString());
    }
}
