package space.sausage.vertxrest.core.handler.mapping;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestMapperTest {
    @Mock
    private BodyMapper readMapper;

    @Mock
    private BodyMapper writeMapper;

    @Mock
    private RoutingContext ctx;

    @Mock
    private HttpServerRequest request;

    private RequestMapper requestMapper;

    @Before
    public void setup() throws MappingException {
        when(ctx.request()).thenReturn(request);
        when(ctx.getBodyAsString()).thenReturn("body");
        when(readMapper.read("body", String.class)).thenReturn("body");

        requestMapper = new RequestMapper(readMapper, writeMapper);
    }

    @Test
    public void readArgTypesOfCtxReturnsArrayOfCtx() throws MappingException {
        List<Class<?>> argTypes = Collections.singletonList(RoutingContext.class);

        Object[] result = requestMapper.read(argTypes, ctx);

        assertThat(result).isEqualTo(new Object[]{ctx});
    }

    @Test
    public void readArgTypesOfRequestReturnsArrayOfRequest() throws MappingException {
        List<Class<?>> argTypes = Collections.singletonList(HttpServerRequest.class);

        Object[] result = requestMapper.read(argTypes, ctx);

        assertThat(result).isEqualTo(new Object[]{ctx.request()});
    }

    @Test
    public void readArgTypesOfCtxAndBodyReturnsArrayOfCtxAndBody() throws MappingException {
        List<Class<?>> argTypes = Arrays.asList(RoutingContext.class, String.class);

        Object[] result = requestMapper.read(argTypes, ctx);

        assertThat(result).isEqualTo(new Object[]{ctx, "body"});
    }

    @Test
    public void readArgTypesOfRequestAndBodyReturnsArrayOfRequestAndBody() throws MappingException {
        List<Class<?>> argTypes = Arrays.asList(HttpServerRequest.class, String.class);

        Object[] result = requestMapper.read(argTypes, ctx);

        assertThat(result).isEqualTo(new Object[]{request, "body"});
    }

    @Test
    public void readArgTypesOfBodyReturnsArrayOfBody() throws MappingException {
        List<Class<?>> argTypes = Collections.singletonList(String.class);

        Object[] result = requestMapper.read(argTypes, ctx);

        assertThat(result).isEqualTo(new Object[]{"body"});
    }

    @Test
    public void writeBodyReturnsMappedBody() throws MappingException {
        byte[] expected = "body".getBytes();
        when(writeMapper.write("body")).thenReturn(expected);

        byte[] actual = requestMapper.write("body");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void equalsSameStateReturnsTrue() {
        RequestMapper other = new RequestMapper(readMapper, writeMapper);

        assertThat(requestMapper).isEqualTo(other);
        assertThat(requestMapper.hashCode()).isEqualTo(other.hashCode());
    }

    @Test
    public void equalsDifferentStateReturnsFalse() {
        RequestMapper other = new RequestMapper(mock(BodyMapper.class), mock(BodyMapper.class));

        assertThat(requestMapper).isNotEqualTo(other);
        assertThat(requestMapper.hashCode()).isNotEqualTo(other.hashCode());
    }
}
