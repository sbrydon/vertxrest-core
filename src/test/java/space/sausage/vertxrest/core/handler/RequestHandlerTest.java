package space.sausage.vertxrest.core.handler;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import space.sausage.vertxrest.core.handler.mapping.MappingException;
import space.sausage.vertxrest.core.handler.mapping.RequestMapper;
import space.sausage.vertxrest.core.registration.ControllerMethod;
import space.sausage.vertxrest.core.registration.MethodInvokeException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerTest {
    @Mock
    private ControllerMethod method;

    @Mock
    private RequestMapper mapper;

    @Mock
    private RoutingContext ctx;

    @Mock
    private HttpServerResponse response;

    private RequestHandler handler;

    @Before
    public void setup() throws MappingException, MethodInvokeException {
        Object[] args = new Object[]{ctx};
        when(method.getArgTypes()).thenReturn(Collections.singletonList(RoutingContext.class));
        when(mapper.read(method.getArgTypes(), ctx)).thenReturn(args);

        when(method.invoke(args)).thenReturn("body");
        when(mapper.write("body")).thenReturn("body".getBytes());
        when(ctx.response()).thenReturn(response);

        handler = new RequestHandler(method, mapper);
    }

    @Test
    public void handleHasProducesTrueWritesBufferAndEnds() {
        when(method.hasProduces()).thenReturn(true);

        handler.handle(ctx);

        ArgumentCaptor<Buffer> captor = ArgumentCaptor.forClass(Buffer.class);
        verify(response).end(captor.capture());
        assertThat(captor.getValue().getBytes()).isEqualTo("body".getBytes());
    }

    @Test
    public void handleHasProducesFalseJustEnds() {
        handler.handle(ctx);
        verify(response).end();
    }

    @Test
    public void handleCatchesMappingExceptionSetsStatus500AndEnds() throws MappingException {
        when(response.setStatusCode(500)).thenReturn(response);
        when(mapper.read(any(), any())).thenThrow(new MappingException("oops", null));

        handler.handle(ctx);

        verify(response).end();
    }

    @Test
    public void handleCatchesMethodInvokeExceptionSetsStatus500AndEnds() throws MethodInvokeException {
        when(response.setStatusCode(500)).thenReturn(response);
        when(method.invoke(any())).thenThrow(new MethodInvokeException("oops", null));

        handler.handle(ctx);

        verify(response).end();
    }

    @Test
    public void equalsSameStateReturnsTrue() {
        RequestHandler other = new RequestHandler(method, mapper);

        assertThat(handler).isEqualTo(other);
        assertThat(handler.hashCode()).isEqualTo(other.hashCode());
    }

    @Test
    public void equalsDifferentStateReturnsFalse() {
        RequestHandler other =
                new RequestHandler(mock(ControllerMethod.class), mock(RequestMapper.class));

        assertThat(handler).isNotEqualTo(other);
        assertThat(handler.hashCode()).isNotEqualTo(other.hashCode());
    }
}
