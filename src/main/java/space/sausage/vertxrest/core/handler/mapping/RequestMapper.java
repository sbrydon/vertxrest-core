package space.sausage.vertxrest.core.handler.mapping;

import com.sun.istack.internal.Nullable;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Objects;

/**
 * Maps a request's arguments and result
 */
public class RequestMapper {
    private final BodyMapper readMapper;
    private final BodyMapper writeMapper;

    /**
     * @param readMapper the mapper for reading the request body
     * @param writeMapper the mapper for writing the response body
     */
    public RequestMapper(@Nullable BodyMapper readMapper, @Nullable BodyMapper writeMapper) {
        this.readMapper = readMapper;
        this.writeMapper = writeMapper;
    }

    /**
     * Map a request's argument types to a context variable and/or body
     * @param argTypes the request's argument types
     * @param ctx the request's context
     * @return instances of the argument types
     * @throws MappingException if a deserialization exception occurred
     */
    public Object[] read(List<Class<?>> argTypes, RoutingContext ctx) throws MappingException {
        Object[] args = new Object[argTypes.size()];
        boolean bodyMapped = false;

        for (int i = 0; i < argTypes.size(); i++) {
            Class<?> type = argTypes.get(i);
            Object ctxArg = getContextArg(type, ctx);

            if (ctxArg != null) {
                args[i] = ctxArg;
                continue;
            }

            if (!bodyMapped) {
                args[i] = readMapper.read(ctx.getBodyAsString(), type);
                bodyMapped = true;
            }
        }

        return args;
    }

    /**
     * Serialize a response's body
     * @param value the response body
     * @return a byte array
     * @throws MappingException if a serialization exception occurred
     */
    public byte[] write(Object value) throws MappingException {
        return writeMapper.write(value);
    }

    private Object getContextArg(Class<?> type, RoutingContext ctx) {
        if (type.isAssignableFrom(RoutingContext.class)) {
            return ctx;
        }

        if (type.isAssignableFrom(HttpServerRequest.class)) {
            return ctx.request();
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapper that = (RequestMapper) o;
        return Objects.equals(readMapper, that.readMapper) &&
                Objects.equals(writeMapper, that.writeMapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readMapper, writeMapper);
    }
}
