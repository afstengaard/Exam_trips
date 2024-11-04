package dat.routes;

import dat.controllers.impl.TripController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;


/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
public class TripRoute {
    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes(){
        return () -> {
            // Static, specific routes without path parameters
            get("/totalprice", tripController::getTotalPriceByGuide);
            post("/populate", tripController::populate);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip);
            get("/category/{category}", tripController::readByCategory);
            get("/", tripController::readAll);
            post("/", tripController::create);
            get("/{id}", tripController::read);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);

        };
    }
}
