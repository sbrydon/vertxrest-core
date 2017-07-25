package space.sausage.vertxrest.core.handler.mapping;

import java.nio.charset.StandardCharsets;

/**
 * Maps bodies to and from plain text
 */
public class TextBodyMapper implements BodyMapper {
    @Override
    public Object read(String value, Class<?> type) throws MappingException {
        return value;
    }

    @Override
    public byte[] write(Object value) throws MappingException {
        if (!(value instanceof String)) {
            IllegalArgumentException e = new IllegalArgumentException("Body not an instance of String");
            throw new MappingException("Unable to write body as Text", e);
        }

        return ((String) value).getBytes(StandardCharsets.UTF_8);
    }
}
