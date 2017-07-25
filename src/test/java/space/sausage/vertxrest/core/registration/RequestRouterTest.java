package space.sausage.vertxrest.core.registration;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import space.sausage.vertxrest.core.annotation.MediaType;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.handler.RequestHandler;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestRouterTest {
    @Mock
    private Router router;

    @Mock
    private Route catchAllRoute;

    @Before
    public void setup() {
        when(router.route()).thenReturn(catchAllRoute);
    }

    @Test
    public void ctorAddResponseAndBodyHandlers() {
        new RequestRouter(router);

        verify(catchAllRoute).handler(isA(BodyHandler.class));
        verify(catchAllRoute).handler(isA(ResponseContentTypeHandler.class));
    }

    @Test
    public void addRequestHandlerSetsBothProducesAndConsumes() {
        RouteParams params = new MockRouteParams()
                .path(HttpMethod.GET, "/path")
                .consumes(MediaType.JSON)
                .produces(MediaType.JSON);

        Route route = getRouteForPath(params.getPath());
        RequestHandler handler = getRequestHandler(params);

        new RequestRouter(router).add(handler);

        verify(route).consumes(MediaType.JSON.toString());
        verify(route).produces(MediaType.JSON.toString());
        verify(route).handler(handler);
    }

    @Test
    public void addRequestHandlerSetsOnlyProduces() {
        RouteParams params = new MockRouteParams()
                .path(HttpMethod.GET, "/path")
                .produces(MediaType.JSON);

        Route route = getRouteForPath(params.getPath());
        RequestHandler handler = getRequestHandler(params);

        new RequestRouter(router).add(handler);

        verify(route).produces(MediaType.JSON.toString());
        verify(route, never()).consumes(any());
        verify(route).handler(handler);
    }

    @Test
    public void addRequestHandlerSetsOnlyConsumes() {
        RouteParams params = new MockRouteParams()
                .path(HttpMethod.GET, "/path")
                .consumes(MediaType.JSON);

        Route route = getRouteForPath(params.getPath());
        RequestHandler handler = getRequestHandler(params);

        new RequestRouter(router).add(handler);

        verify(route, never()).produces(any());
        verify(route).consumes(MediaType.JSON.toString());
        verify(route).handler(handler);
    }

    @Test
    public void acceptCallRouterAccept() {
        HttpServerRequest request = mock(HttpServerRequest.class);

        new RequestRouter(router).accept(request);

        verify(router).accept(request);
    }

    private Route getRouteForPath(Path path) {
        Route route = mock(Route.class);
        when(router.route(path.method(), path.path())).thenReturn(route);

        return route;
    }

    private RequestHandler getRequestHandler(RouteParams params) {
        RequestHandler handler = mock(RequestHandler.class);
        when(handler.getParams()).thenReturn(params);

        return handler;
    }
}
