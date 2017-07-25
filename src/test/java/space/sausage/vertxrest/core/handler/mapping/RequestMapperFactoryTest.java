package space.sausage.vertxrest.core.handler.mapping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import space.sausage.vertxrest.core.annotation.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestMapperFactoryTest {
    @Mock
    private BodyMapperFactory bodyMapperFactory;

    @Test
    public void createWithReadAndWriteTypesCreatesMapper() {
        BodyMapper readMapper = mock(JsonBodyMapper.class);
        BodyMapper writeMapper = mock(JsonBodyMapper.class);
        when(bodyMapperFactory.create(MediaType.JSON)).thenReturn(readMapper).thenReturn(writeMapper);

        RequestMapperFactory factory = new RequestMapperFactory(bodyMapperFactory);
        RequestMapper mapper = factory.create(MediaType.JSON, MediaType.JSON);

        assertThat(mapper).isEqualTo(new RequestMapper(readMapper, writeMapper));
    }

    @Test
    public void createWithReadTypeCreatesMapper() {
        BodyMapper readMapper = mock(TextBodyMapper.class);
        when(bodyMapperFactory.create(MediaType.PLAIN_TEXT)).thenReturn(readMapper);

        RequestMapperFactory factory = new RequestMapperFactory(bodyMapperFactory);
        RequestMapper mapper = factory.create(MediaType.PLAIN_TEXT, null);

        assertThat(mapper).isEqualTo(new RequestMapper(readMapper, null));
    }

    @Test
    public void createWithWriteTypeCreatesMapper() {
        BodyMapper writeMapper = mock(TextBodyMapper.class);
        when(bodyMapperFactory.create(MediaType.PLAIN_TEXT)).thenReturn(writeMapper);

        RequestMapperFactory factory = new RequestMapperFactory(bodyMapperFactory);
        RequestMapper mapper = factory.create(null, MediaType.PLAIN_TEXT);

        assertThat(mapper).isEqualTo(new RequestMapper(null, writeMapper));
    }
}
