package space.sausage.vertxrest.core.handler.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonBodyMapperTest {
    @Mock
    private ObjectMapper objectMapper;

    @Test(expected = MappingException.class)
    public void readThrowIOExceptionThrowsMappingException() throws IOException, MappingException {
        when(objectMapper.readValue("body", String.class)).thenThrow(new IOException());
        new JsonBodyMapper(objectMapper).read("body", String.class);
    }

    @Test(expected = MappingException.class)
    public void writeThrowIOExceptionThrowsMappingException() throws JsonProcessingException, MappingException {
        when(objectMapper.writeValueAsBytes("body")).thenThrow(new TestJsonProcessingException());
        new JsonBodyMapper(objectMapper).write("body");
    }

    class TestJsonProcessingException extends JsonProcessingException {
        protected TestJsonProcessingException() {
            super("oops");
        }
    }
}
