package space.sausage.vertxrest.core.handler;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import space.sausage.vertxrest.core.annotation.MediaType;
import space.sausage.vertxrest.core.handler.mapping.RequestMapper;
import space.sausage.vertxrest.core.handler.mapping.RequestMapperFactory;
import space.sausage.vertxrest.core.registration.ControllerMethod;

import java.lang.reflect.Method;

/**
 * A factory for creating {@link RequestHandler}s
 */
@Singleton
public class RequestHandlerFactory {
    private final Injector injector;
    private final RequestMapperFactory factory;

    @Inject
    RequestHandlerFactory(Injector injector, RequestMapperFactory factory) {
        this.injector = injector;
        this.factory = factory;
    }

    /**
     * @param type the controller type
     * @param method the annotated method
     * @return A {@link RequestHandler}
     */
    public RequestHandler create(Class<?> type, Method method) {
        Object controller = injector.getInstance(type);
        ControllerMethod controllerMethod = new ControllerMethod(controller, method);

        MediaType readType = controllerMethod.hasConsumes() ?
                controllerMethod.getConsumes().value() :
                null;

        MediaType writeType = controllerMethod.hasProduces() ?
                controllerMethod.getProduces().value() :
                null;

        RequestMapper mapper = factory.create(readType, writeType);
        return new RequestHandler(controllerMethod, mapper);
    }
}
