package space.sausage.vertxrest.core.registration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.handler.RequestHandler;
import space.sausage.vertxrest.core.handler.RequestHandlerFactory;

import java.lang.reflect.Method;

/**
 * Central registry for controllers
 */
@Singleton
public class ControllerRegistry {
    private final RequestRouter router;
    private final RequestHandlerFactory factory;

    @Inject
    ControllerRegistry(RequestRouter router, RequestHandlerFactory factory) {
        this.router = router;
        this.factory = factory;
    }

    /**
     * Maps annotated controller types to {@link RequestHandler}s
     * @param type the controller type
     */
    public void register(Class<?> type) {
        for (Method method : type.getMethods()) {
            Path path = method.getAnnotation(Path.class);
            if (path == null) {
                continue;
            }

            RequestHandler handler = factory.create(type, method);
            router.add(handler);
        }
    }
}
