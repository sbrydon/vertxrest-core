package space.sausage.vertxrest.core.annotation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MediaTypeTest {
    @Test
    public void plainTextToStringEqualsHttpContentType() {
        assertThat(MediaType.PLAIN_TEXT).hasToString("text/plain");
    }

    @Test
    public void jsonToStringEqualsHttpContentType() {
        assertThat(MediaType.JSON).hasToString("application/json");
    }
}
