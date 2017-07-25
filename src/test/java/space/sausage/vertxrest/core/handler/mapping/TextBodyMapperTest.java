package space.sausage.vertxrest.core.handler.mapping;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class TextBodyMapperTest {
    @Test
    public void readStringReturnsString() throws MappingException {
        String expected = "body";

        Object actual = new TextBodyMapper().read(expected, String.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = MappingException.class)
    public void writeNonStringThrowsMappingException() throws MappingException {
        new TextBodyMapper().write(1);
    }

    @Test
    public void writeStringReturnsUtf8Bytes() throws MappingException {
        byte[] expected = "body".getBytes(StandardCharsets.UTF_8);

        byte[] actual = new TextBodyMapper().write("body");

        assertThat(actual).isEqualTo(expected);
    }
}
