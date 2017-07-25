package space.sausage.vertxrest.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.Vertx;
import space.sausage.vertxrest.core.registration.ControllerRegistry;
import space.sausage.vertxrest.core.registration.RequestRouter;

/**
 * {@link VertxRest} builder
 */
public class VertxRestBuilder {
    private final VertxRestModule module = new VertxRestModule();

    private Vertx vertx;
    private RequestRouter router;
    private int port;

    /**
     * @return the {@link Vertx} instance
     */
    Vertx vertx() {
        return vertx;
    }

    /**
     * @return the {@link RequestRouter} instance
     */
    RequestRouter router() {
        return router;
    }

    /**
     * @return the server port
     */
    int port() {
        return port;
    }

    /**
     * Add an annotated controller type
     * @param type the controller type you'd like to add
     * @return a reference to this
     * @see space.sausage.vertxrest.core.annotation.Path
     */
    public VertxRestBuilder controller(Class<?> type) {
        module.addControllerType(type);
        return this;
    }

    /**
     * Set the server port (Optional - defaults to 8080)
     * @param port the port to listen on
     * @return a reference to this
     */
    public VertxRestBuilder port(int port) {
        this.port = port;
        return this;
    }

    /**
     * @return a {@link VertxRest} instance
     */
    public VertxRest build() {
        if (port == 0) {
            port = 8080;
        }

        Injector injector = Guice.createInjector(module);

        ControllerRegistry registry = injector.getInstance(ControllerRegistry.class);
        for (Class<?> type : module.getControllerTypes()) {
            registry.register(type);
        }

        vertx = injector.getInstance(Vertx.class);
        router = injector.getInstance(RequestRouter.class);

        return new VertxRest(this);
    }
}
