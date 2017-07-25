package space.sausage.vertxrest.core.registration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.handler.RequestHandler;

@Singleton
public class RequestRouter {
    private final static Logger logger = LoggerFactory.getLogger(RequestRouter.class);

    private final Router router;

    @Inject
    RequestRouter(Router router) {
        this.router = router;

        router.route().handler(BodyHandler.create());
        router.route().handler(ResponseContentTypeHandler.create());
    }

    void add(RequestHandler handler) {
        RouteParams params = handler.getParams();

        Path path = params.getPath();
        Route route = router.route(path.method(), path.path());

        if (params.hasConsumes()) {
            route.consumes(params.getConsumes().value().toString());
        }

        if (params.hasProduces()) {
            route.produces(params.getProduces().value().toString());
        }

        route.handler(handler);
        logger.info("Added {}", handler);
    }

    public void accept(HttpServerRequest request) {
        router.accept(request);
    }
}
