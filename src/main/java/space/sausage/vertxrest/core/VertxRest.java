package space.sausage.vertxrest.core;

import io.vertx.core.Vertx;
import space.sausage.vertxrest.core.registration.RequestRouter;

// TODO: Make vertical for horizontal scaling

/**
 * VertxRest server
 * @see #start()
 */
public class VertxRest {
    private final Vertx vertx;
    private final RequestRouter router;
    private final int port;

    /**
     * Builder constructor
     * @param builder the {@link VertxRestBuilder} instance
     */
    VertxRest(VertxRestBuilder builder) {
        this.vertx = builder.vertx();
        this.router = builder.router();
        this.port = builder.port();
    }

    /**
     * Start the server using parameters provided to {@link VertxRestBuilder}
     */
    public void start() {
        vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }
}
