package dat.daos.impl;

import dat.daos.IDAO;
import dat.daos.ITripGuideDAO;
import dat.dtos.GuideTotalPriceDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripDAO implements IDAO<TripDTO, Long>, ITripGuideDAO {

    private static TripDAO instance;
    private static GuideDAO guideDAO;
    private static EntityManagerFactory emf;

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
            guideDAO = GuideDAO.getInstance(emf);
        }
        return instance;
    }

    @Override
    public List<TripDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t", TripDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide = em.find(Guide.class, tripDTO.getGuideId());
            Trip trip = new Trip(tripDTO);
            trip.setGuide(guide);

            em.persist(trip);
            em.getTransaction().commit();

            return new TripDTO(trip);
        }
    }


    @Override
    public TripDTO update(Long id, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                trip.setStarttime(tripDTO.getStarttime());
                trip.setEndtime(tripDTO.getEndtime());
                trip.setLongitude(tripDTO.getLongitude());
                trip.setLatitude(tripDTO.getLatitude());
                trip.setName(tripDTO.getName());
                trip.setPrice(tripDTO.getPrice());
                trip.setCategory(tripDTO.getCategory());

                Guide guide = em.find(Guide.class, tripDTO.getGuideId());
                trip.setGuide(guide);

                Trip mergedTrip = em.merge(trip);
                em.getTransaction().commit();
                return new TripDTO(mergedTrip);
            }

            return null;
        }
    }


    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                em.remove(trip);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Trip.class, id) != null;
        }
    }

    @Override
    public void addGuideToTrip(long tripId, long guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, (long) tripId);
            Guide guide = em.find(Guide.class, (long) guideId);
            trip.setGuide(guide);
            em.merge(trip);
            em.getTransaction().commit();
        }
    }

    // Implementing getTripsByGuide
    @Override
    public Set<TripDTO> getTripsByGuide(long guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery(
                    "SELECT new dat.dtos.TripDTO(t) FROM Trip t WHERE t.guide.id = :guideId", TripDTO.class);
            query.setParameter("guideId", guideId);
            return new HashSet<>(query.getResultList());
        }
    }

    public void populate() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Convert GuideDTO to Guide entities
            Guide guideJohn = new Guide("John", "Doe", "john.doe@example.com", "1234567890", 10);
            Guide guideJane = new Guide("Jane", "Smith", "jane.smith@example.com", "0987654321", 5);

            // Get Trip sets and associate them with the guides
            List<Trip> johnTrips = getJohnTrips(guideJohn);
            List<Trip> janeTrips = getJaneTrips(guideJane);

            guideJohn.setTrips(johnTrips);
            guideJane.setTrips(janeTrips);

            // Persist the guides along with their trips
            em.persist(guideJohn);
            em.persist(guideJane);

            em.getTransaction().commit();
        }
    }

    // Helper methods to create Trips for each Guide
    private List<Trip> getJohnTrips(Guide guide) {
        Trip trip1 = new Trip(LocalDateTime.of(2023, 1, 1, 9, 0),
                LocalDateTime.of(2023, 1, 1, 17, 0),
                45.0, -122.0, "Beach Adventure",
                100.0, Trip.Category.BEACH, guide);

        Trip trip2 = new Trip(LocalDateTime.of(2023, 2, 1, 10, 0),
                LocalDateTime.of(2023, 2, 1, 18, 0),
                44.5, -123.5, "Forest Hike",
                100.0, Trip.Category.FOREST, guide);

        return List.of(trip1, trip2);
    }

    private List<Trip> getJaneTrips(Guide guide) {
        Trip trip1 = new Trip(LocalDateTime.of(2023, 3, 1, 8, 0),
                LocalDateTime.of(2023, 3, 1, 15, 0),
                46.0, -121.5, "Snow Excursion",
                200.0, Trip.Category.SNOW, guide);

        Trip trip2 = new Trip(LocalDateTime.of(2023, 4, 1, 7, 0),
                LocalDateTime.of(2023, 4, 1, 16, 0),
                47.5, -120.5, "Lake Escape",
                120.0, Trip.Category.LAKE, guide);

        return List.of(trip1, trip2);
    }

    public List<TripDTO> readByCategory(String category) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery(
                    "SELECT new dat.dtos.TripDTO(t) FROM Trip t WHERE t.category = :category", TripDTO.class);
            query.setParameter("category", Trip.Category.valueOf(category.toUpperCase()));
            return query.getResultList();
        }
    }

    public List<GuideTotalPriceDTO> getTotalPriceByGuide() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT new dat.dtos.GuideTotalPriceDTO(t.guide.id, SUM(t.price)) " +
                    "FROM Trip t GROUP BY t.guide.id";
            TypedQuery<GuideTotalPriceDTO> query = em.createQuery(jpql, GuideTotalPriceDTO.class);
            return query.getResultList();
        }
    }

}
