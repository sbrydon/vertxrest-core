package space.sausage.vertxrest.core.handler;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.sausage.vertxrest.core.handler.mapping.MappingException;
import space.sausage.vertxrest.core.handler.mapping.RequestMapper;
import space.sausage.vertxrest.core.registration.ControllerMethod;
import space.sausage.vertxrest.core.registration.MethodInvokeException;
import space.sausage.vertxrest.core.registration.RouteParams;

import java.util.Objects;

/**
 * Handler for Vert.x-Web's requests that wraps a controller's annotated method
 */
public class RequestHandler implements Handler<RoutingContext> {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final ControllerMethod method;
    private final RequestMapper mapper;

    public RouteParams getParams() {
        return method;
    }

    /**
     * @param method the method to invoke when handling a request
     * @param mapper the mapper to use for reading arg types and writing results
     */
    RequestHandler(ControllerMethod method, RequestMapper mapper) {
        this.method = method;
        this.mapper = mapper;
    }

    @Override
    public void handle(RoutingContext ctx) {
        try {
            Object[] args = mapper.read(method.getArgTypes(), ctx);
            Object result = method.invoke(args);

            if (method.hasProduces()) {
                Buffer buffer = Buffer.buffer(mapper.write(result));
                ctx.response().end(buffer);
            } else {
                ctx.response().end();
            }
        } catch (MappingException | MethodInvokeException e) {
            logger.error("Unable to handle request", e);
            ctx.response().setStatusCode(500).end();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHandler that = (RequestHandler) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(mapper, that.mapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, mapper);
    }

    @Override
    public String toString() {
        return "RequestHandler: " + method;
    }
}
