package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Trip;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {

    private Long id;

    @JsonProperty("starttime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime starttime;

    @JsonProperty("endtime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endtime;

    private Double longitude;
    private Double latitude;
    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Trip.Category category;

    private Long guideId;

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.starttime = trip.getStarttime();
        this.endtime = trip.getEndtime();
        this.longitude = trip.getLongitude();
        this.latitude = trip.getLatitude();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.guideId = trip.getGuide().getId();
    }

    //Contructor without id
    public TripDTO(LocalDateTime starttime, LocalDateTime endtime, Double longitude, Double latitude, String name, Double price, Trip.Category category, Long guideId) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guideId = guideId;
    }
}
