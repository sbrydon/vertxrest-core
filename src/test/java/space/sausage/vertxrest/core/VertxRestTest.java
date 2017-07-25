package space.sausage.vertxrest.core;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import space.sausage.vertxrest.core.registration.RequestRouter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VertxRestTest {
    @Mock
    private Vertx vertx;

    @Mock
    private RequestRouter router;

    @Mock
    private VertxRestBuilder builder;

    @Before
    public void setup() {
        when(builder.vertx()).thenReturn(vertx);
        when(builder.router()).thenReturn(router);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void startAddRouterAsHandlerAndListenOnPort() {
        when(builder.port()).thenReturn(42020);

        HttpServer server = mock(HttpServer.class);
        when(server.requestHandler(any())).thenReturn(server);
        when(vertx.createHttpServer()).thenReturn(server);

        new VertxRest(builder).start();

        ArgumentCaptor<Handler<HttpServerRequest>> captor = ArgumentCaptor.forClass(Handler.class);
        verify(server).requestHandler(captor.capture());
        HttpServerRequest request = mock(HttpServerRequest.class);
        captor.getValue().handle(request);

        verify(router).accept(request);
        verify(server).listen(42020);
    }
}
