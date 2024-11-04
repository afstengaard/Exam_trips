package dat.entities;

import dat.daos.impl.GuideDAO;
import dat.dtos.TripDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starttime", nullable = false)
    private LocalDateTime starttime;

    @Column(name = "endtime", nullable = false)
    private LocalDateTime endtime;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;


    public Trip(TripDTO tripDTO) {
        this.starttime = tripDTO.getStarttime();
        this.endtime = tripDTO.getEndtime();
        this.longitude = tripDTO.getLongitude();
        this.latitude = tripDTO.getLatitude();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();
        this.guide = new Guide();
    }

    public Trip(LocalDateTime starttime, LocalDateTime endtime, Double longitude, Double latitude, String name, Double price, Category category, Guide guide) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

    public enum Category {
        BEACH, CITY, FOREST, LAKE, SEA, SNOW
    }
}
