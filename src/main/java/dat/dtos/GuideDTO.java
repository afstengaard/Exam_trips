package dat.dtos;

import dat.entities.Guide;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GuideDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int yearsOfExperience;
    private List<TripDTO> trips;

    public GuideDTO(Guide guide) {
        this.id = guide.getId();
        this.firstname = guide.getFirstname();
        this.lastname = guide.getLastname();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.yearsOfExperience = guide.getYearsOfExperience();
        this.trips = guide.getTrips().stream().map(TripDTO::new).toList();
    }

    public GuideDTO (String firstname, String lastname, String email, String phone, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }
}
