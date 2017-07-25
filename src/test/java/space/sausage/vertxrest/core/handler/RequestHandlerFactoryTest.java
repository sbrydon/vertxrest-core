package space.sausage.vertxrest.core.handler;

import com.google.inject.Injector;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import space.sausage.vertxrest.core.annotation.Consumes;
import space.sausage.vertxrest.core.annotation.MediaType;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.annotation.Produces;
import space.sausage.vertxrest.core.handler.mapping.BodyMapper;
import space.sausage.vertxrest.core.handler.mapping.RequestMapper;
import space.sausage.vertxrest.core.handler.mapping.RequestMapperFactory;
import space.sausage.vertxrest.core.registration.ControllerMethod;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerFactoryTest {
    @Mock
    private Injector injector;

    @Mock
    private RequestMapperFactory mapperFactory;

    @Test
    public void createConstructsRequestHandler() throws NoSuchMethodException {
        Controller controller = new Controller();
        when(injector.getInstance(Controller.class)).thenReturn(controller);

        RequestMapper mapper = new RequestMapper(mock(BodyMapper.class), mock(BodyMapper.class));
        when(mapperFactory.create(MediaType.PLAIN_TEXT, MediaType.PLAIN_TEXT)).thenReturn(mapper);

        ControllerMethod method = new ControllerMethod(controller, controller.method());
        RequestHandler expected = new RequestHandler(method, mapper);

        RequestHandlerFactory factory = new RequestHandlerFactory(injector, mapperFactory);
        RequestHandler actual = factory.create(Controller.class, controller.method());

        assertThat(actual).isEqualTo(expected);
    }

    class Controller {
        @Path(method = HttpMethod.GET, path = "/hello")
        @Consumes(MediaType.PLAIN_TEXT)
        @Produces(MediaType.PLAIN_TEXT)
        public void get(HttpServerRequest request) {
        }

        public Method method() throws NoSuchMethodException {
            return getClass().getMethod("get", HttpServerRequest.class);
        }
    }
}
