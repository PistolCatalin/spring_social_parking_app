package social.parking.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReportEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportId;
    @OneToOne
    @JoinColumn(name = "spotId", referencedColumnName = "spotId")
    private ParkingSpot parkingSpot;
    private String type;
    @Column(columnDefinition = "text")
    private String description;

}
