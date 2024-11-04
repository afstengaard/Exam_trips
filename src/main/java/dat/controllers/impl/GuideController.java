package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.GuideDAO;
import dat.daos.impl.TripDAO;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
public class GuideController implements IController {

    private final GuideDAO dao;

    public GuideController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = GuideDAO.getInstance(emf);
    }


    @Override
    public void read(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        GuideDTO guide = dao.read(id);
        if (guide == null) {
            throw new ApiException(404, "Guide with ID " + id + " not found");
        }
        ctx.json(guide).status(200);
    }

    @Override
    public void readAll(Context ctx) {
        List<GuideDTO> trips = dao.readAll();
        ctx.json(trips).status(200);
    }

    @Override
    public void create(Context ctx) {
        GuideDTO guide = ctx.bodyValidator(GuideDTO.class)
                .check(g -> g.getFirstname() != null && !g.getFirstname().isEmpty(), "First name is required")
                .check(g -> g.getLastname() != null && !g.getLastname().isEmpty(), "Last name is required")
                .check(g -> g.getEmail() != null && !g.getEmail().isEmpty(), "Email is required")
                .check(g -> g.getPhone() != null && !g.getPhone().isEmpty(), "Phone is required")
                .get();

        ctx.json(dao.create(guide)).status(201);
    }

    @Override
    public void update(Context ctx) {

    }

    @Override
    public void delete(Context ctx) {

    }

    @Override
    public boolean validatePrimaryKey(Object o) {
        return false;
    }

    @Override
    public Object validateEntity(Context ctx) {
        return null;
    }
}
