package space.sausage.vertxrest.core.registration;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.handler.RequestHandler;
import space.sausage.vertxrest.core.handler.RequestHandlerFactory;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerRegistryTest {
    @Mock
    private RequestRouter router;

    @Mock
    private RequestHandlerFactory factory;

    @Test
    public void registerPathNotNullAddRequestHandler() throws NoSuchMethodException {
        PathNotNullController controller = new PathNotNullController();

        RequestHandler handler = mock(RequestHandler.class);
        when(factory.create(PathNotNullController.class, controller.method())).thenReturn(handler);

        new ControllerRegistry(router, factory).register(PathNotNullController.class);

        verify(router).add(handler);
    }

    @Test
    public void registerPathIsNullDoesNotAddRequestHandler() {
        new ControllerRegistry(router, factory).register(PathIsNullController.class);

        verify(router, never()).add(any(RequestHandler.class));
    }

    class EmptyController {
    }

    class PathNotNullController {
        @Path(method = HttpMethod.GET, path = "/hello")
        public void get(HttpServerRequest request) {
        }

        public Method method() throws NoSuchMethodException {
            return getClass().getMethod("get", HttpServerRequest.class);
        }
    }

    class PathIsNullController {
        public void get(HttpServerRequest request) {
        }
    }
}
