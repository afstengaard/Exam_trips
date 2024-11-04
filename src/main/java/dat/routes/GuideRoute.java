package dat.routes;

import dat.controllers.impl.GuideController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
public class GuideRoute {
    private final GuideController guideController = new GuideController();

    protected EndpointGroup getRoutes(){
        return () -> {
            post("/", guideController::create);
            get("/", guideController::readAll);
            get("/{id}", guideController::read);
        };
    }
}
