package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.daos.impl.TripDAO;
import dat.dtos.GuideTotalPriceDTO;
import dat.dtos.TripDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TripController {

    private final TripDAO dao;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = TripDAO.getInstance(emf);
    }

    public void readAll(Context ctx) {
        List<TripDTO> trips = dao.readAll();
        ctx.json(trips).status(200);
    }

    public void read(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        TripDTO trip = dao.read(id);
        if (trip == null) {
            throw new ApiException(404, "Trip with ID " + id + " not found");
        }
        //Logik til at håndtere pakkeliste
        ctx.json(trip).status(200);
    }

    public void create(Context ctx) {
        TripDTO trip = ctx.bodyValidator(TripDTO.class)
                .check(t -> t.getName() != null && !t.getName().isEmpty(), "Name is required")
                .check(t -> t.getPrice() != null && t.getPrice() > 0, "Price must be greater than 0")
                .check(t -> t.getCategory() != null, "Category is required")
                .check(t -> t.getStarttime() != null, "Start time is required")
                .check(t -> t.getEndtime() != null && !t.getEndtime().isBefore(t.getStarttime()), "End time must be after start time")
                .get();

        ctx.json(dao.create(trip)).status(201);
    }

    public void update(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        TripDTO trip = ctx.bodyAsClass(TripDTO.class);
        TripDTO updatedTrip = dao.update(id, trip);
        if (updatedTrip != null) {
            ctx.json(updatedTrip).status(200);
        } else {
            throw new ApiException(404, "Trip with ID " + id + " not found");
        }
    }

    public void delete(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        if (dao.read(id) == null) {
            throw new ApiException(404, "Trip with ID " + id + " not found");
        }
        dao.delete(id);
        ctx.status(204);
    }

    public void addGuideToTrip(Context ctx) {
        long tripId = ctx.pathParamAsClass("tripId", Long.class).get();
        long guideId = ctx.pathParamAsClass("guideId", Long.class).get();

        try {
            dao.addGuideToTrip(tripId, guideId);
            ctx.status(204).result("Guide added to trip successfully");
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    public void readByCategory(Context ctx) {
        String category = ctx.pathParam("category");
        List<TripDTO> trips = dao.readByCategory(category);
        if (trips.isEmpty()) {
            throw new ApiException(404, "No trips found for category " + category);
        }
        ctx.json(trips).status(200);
    }

    public void populate(Context ctx) {
        dao.populate();
        ctx.status(204).result("Trips populated successfully");
    }


    public void getTotalPriceByGuide(Context ctx) {
        List<GuideTotalPriceDTO> result = dao.getTotalPriceByGuide();
        ctx.json(result).status(200);
    }

}