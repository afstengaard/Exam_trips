package dat.controllers.impl;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.controllers.impl.TripController;
import dat.daos.impl.TripDAO;
import dat.dtos.TripDTO;
import dat.entities.Trip;
import io.javalin.Javalin;
import io.restassured.common.mapper.TypeRef;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TripControllerTest {

    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static Javalin app;
    private static TripDAO tripDAO;
    private static TripController tripController;
    private static final String BASE_URL = "http://localhost:7070/api";

    @BeforeAll
    void setUpAll() {
        HibernateConfig.setTest(true);

        // Start the server
        app = ApplicationConfig.startServer(7070);

        tripDAO = TripDAO.getInstance(emf);
        tripController = new TripController();
    }

    @BeforeEach
    void setUp() {
        // Populate the database with trips and guides
        System.out.println("Populating database with trips");
        tripDAO.populate();
        //Der er fejl i måden db bliver håndteret før/under/efter test.
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.createQuery("DELETE FROM Guide").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void readAllTrips() {
        List<TripDTO> trips =
                given()
                        .when()
                        .get(BASE_URL + "/trips")
                        .then()
                        .statusCode(200)
                        .body("size()", is(4))  // Adjust the size if you expect more trips
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<TripDTO>>() {});

        assertThat(trips.size(), is(4)); // Adjust expected size based on the populated data
    }

    @Test
    void readTripById() {
        // Assuming the first trip's ID is 1 (or adjust based on setup data)
        long tripId = 1;

        TripDTO trip = given()
                .when()
                .get(BASE_URL + "/trips/" + tripId)
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(TripDTO.class);

        assertThat(trip.getId(), is(tripId));
    }

    //Virker kun når den køres alene?!?!
    @Test
    void createTrip() {
        TripDTO trip = new TripDTO(LocalDateTime.now(),LocalDateTime.now().plusHours(8),45.0,100.0,"Beach Adventure",100.0, Trip.Category.BEACH,1L);
        TripDTO createdTrip = given()
                .contentType("application/json")
                .body(trip)
                .when()
                .post(BASE_URL + "/trips")
                .then()
                .statusCode(201)
                .log().all()
                .extract()
                .as(TripDTO.class);

        assertThat(createdTrip.getName(), is(trip.getName()));
        assertThat(createdTrip.getCategory(), is(trip.getCategory()));
        assertThat(createdTrip.getPrice(), is(trip.getPrice()));
    }


    //Kan også kun køre alene
    @Test
    void updateTrip(){
        long tripId = tripDAO.readAll().get(0).getId();
        TripDTO trip = new TripDTO(LocalDateTime.now(),LocalDateTime.now().plusHours(8),45.0,100.0,"Beach Adventure",100.0, Trip.Category.BEACH,1L);
        TripDTO updatedTrip = given()
                .contentType("application/json")
                .body(trip)
                .when()
                .put(BASE_URL + "/trips/" + tripId)
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(TripDTO.class);

        assertThat(updatedTrip.getName(), is(trip.getName()));
        assertThat(updatedTrip.getCategory(), is(trip.getCategory()));
        assertThat(updatedTrip.getPrice(), is(trip.getPrice()));
    }

    //Virker også kun alene
    @Test
    void deleteTrip(){
        long tripId = tripDAO.readAll().get(0).getId();
        given()
                .when()
                .delete(BASE_URL + "/trips/" + tripId)
                .then()
                .statusCode(204);

        List<TripDTO> trips = given()
                .when()
                .get(BASE_URL + "/trips")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<TripDTO>>() {});

        assertThat(trips.size(), is(3));
    }






}
