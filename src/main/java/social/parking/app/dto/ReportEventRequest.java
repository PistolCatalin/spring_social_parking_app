package social.parking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportEventRequest {
    private Long id;
    private String latitude;
    private String longitutde;
    private Long price;
    private String status;
    private Long reserved_time;
    private String type; //eg: add parking place , report parking place
    private String description;

}
