package space.sausage.vertxrest.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Guice binding configuration
 * @see #addControllerType(Class)
 * @see #getControllerTypes()
 */
class VertxRestModule extends AbstractModule {
    private final List<Class<?>> controllerTypes = new ArrayList<>();

    /**
     * @return the added controller types
     */
    List<Class<?>> getControllerTypes() {
        return controllerTypes;
    }

    /**
     * Add a controller type for binding during Guice's configuration phase
     * @param type the controller type
     */
    void addControllerType(Class<?> type) {
        controllerTypes.add(type);
    }

    @Override
    protected void configure() {
        Vertx vertx = Vertx.vertx();
        bind(Vertx.class).toInstance(vertx);
        bind(Router.class).toInstance(Router.router(vertx));

        for (Class<?> type : controllerTypes) {
            bind(type).in(Scopes.SINGLETON);
        }
    }
}
