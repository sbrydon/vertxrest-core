package space.sausage.vertxrest.core.handler.mapping;

import org.junit.Test;
import space.sausage.vertxrest.core.annotation.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class BodyMapperFactoryTest {
    @Test
    public void createWithJsonCreatesJsonBodyMapper() {
        BodyMapper mapper = new BodyMapperFactory().create(MediaType.JSON);
        assertThat(mapper).isInstanceOf(JsonBodyMapper.class);
    }

    @Test
    public void createWithPlainTextCreatesTextBodyMapper() {
        BodyMapper mapper = new BodyMapperFactory().create(MediaType.PLAIN_TEXT);
        assertThat(mapper).isInstanceOf(TextBodyMapper.class);
    }
}
