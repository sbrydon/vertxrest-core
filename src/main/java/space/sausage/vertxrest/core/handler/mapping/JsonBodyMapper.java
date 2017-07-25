package space.sausage.vertxrest.core.handler.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Maps bodies to and from JSON
 */
public class JsonBodyMapper implements BodyMapper {
    private final ObjectMapper objectMapper;

    /**
     * @param objectMapper the underlying JSON mapper
     */
    JsonBodyMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object read(String value, Class<?> type) throws MappingException {
        try {
            return objectMapper.readValue(value, type);
        } catch (IOException e) {
            throw new MappingException("Unable to read body as JSON", e);
        }
    }

    @Override
    public byte[] write(Object value) throws MappingException {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new MappingException("Unable to write body as JSON", e);
        }
    }
}
