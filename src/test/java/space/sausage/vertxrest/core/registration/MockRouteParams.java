package space.sausage.vertxrest.core.registration;

import io.vertx.core.http.HttpMethod;
import space.sausage.vertxrest.core.annotation.Consumes;
import space.sausage.vertxrest.core.annotation.MediaType;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.annotation.Produces;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockRouteParams implements RouteParams {
    private Path path;
    private Consumes consumes;
    private Produces produces;

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Consumes getConsumes() {
        return consumes;
    }

    @Override
    public Produces getProduces() {
        return produces;
    }

    @Override
    public boolean hasConsumes() {
        return consumes != null;
    }

    @Override
    public boolean hasProduces() {
        return produces != null;
    }

    MockRouteParams path(HttpMethod method, String path) {
        this.path = mock(Path.class);
        when(this.path.method()).thenReturn(method);
        when(this.path.path()).thenReturn(path);

        return this;
    }

    MockRouteParams consumes(MediaType type) {
        if (type == null) {
            consumes = null;
            return this;
        }

        consumes = mock(Consumes.class);
        when(consumes.value()).thenReturn(type);

        return this;
    }

    MockRouteParams produces(MediaType type) {
        if (type == null) {
            produces = null;
            return this;
        }

        produces = mock(Produces.class);
        when(produces.value()).thenReturn(type);

        return this;
    }
}
