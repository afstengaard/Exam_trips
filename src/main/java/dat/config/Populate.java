package dat.config;

import dat.dtos.TripDTO;
import dat.dtos.GuideDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // Create GuideDTOs
        GuideDTO guide1 = new GuideDTO("John", "Doe", "john.doe@example.com", "1234567890", 10);
        GuideDTO guide2 = new GuideDTO("Jane", "Doe", "jane.doe@example.com", "0987654321", 5);

        TripDTO trip1 = new TripDTO(LocalDateTime.of(2023, 1, 1, 9, 0), LocalDateTime.of(2023, 1, 1, 17, 0), 45.0, -122.0, "Beach Adventure", 100.0, Trip.Category.BEACH, guide1.getId());
        TripDTO trip2 = new TripDTO(LocalDateTime.of(2023, 2, 1, 10, 0), LocalDateTime.of(2023, 2, 1, 18, 0), 44.5, -123.5, "Forest Hike", 150.0, Trip.Category.FOREST, guide1.getId());
        TripDTO trip3 = new TripDTO(LocalDateTime.of(2023, 3, 1, 8, 0), LocalDateTime.of(2023, 3, 1, 15, 0), 46.0, -121.5, "Snow Excursion", 200.0, Trip.Category.SNOW, guide2.getId());
        TripDTO trip4 = new TripDTO(LocalDateTime.of(2023, 4, 1, 7, 0), LocalDateTime.of(2023, 4, 1, 16, 0), 47.5, -120.5, "Lake Escape", 120.0, Trip.Category.LAKE, guide2.getId());

        // Set Trips for each Guide
        guide1.setTrips(List.of(trip1, trip2));
        guide2.setTrips(List.of(trip3, trip4));

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persist the Guide entities with associated Trips
            em.persist(new Guide(guide1));
            em.persist(new Guide(guide2));

            em.getTransaction().commit();
        }
    }
}
