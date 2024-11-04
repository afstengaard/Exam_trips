package dat.entities;

import dat.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "guide")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.PERSIST)
    private List<Trip> trips;

    public Guide(GuideDTO guideDTO){
        this.firstname = guideDTO.getFirstname();
        this.lastname = guideDTO.getLastname();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
        this.trips = guideDTO.getTrips().stream().map(tripDTO -> {
            Trip trip = new Trip(tripDTO);
            trip.setGuide(this);  // Set the guide reference in each Trip
            return trip;
        }).collect(Collectors.toList());
    }

    public Guide(String firstname, String lastname, String email, String phone, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }
}
