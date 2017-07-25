package space.sausage.vertxrest.core.handler.mapping;

/**
 * An interface for reading request bodies and writing response bodies
 */
public interface BodyMapper {
    /**
     * Map a request body to an instance of a type
     * @param value the body
     * @param type the type
     * @return an instance of the type
     * @throws MappingException if mapping causes in a deserialization exception
     */
    Object read(String value, Class<?> type) throws MappingException;

    /**
     * Map a response body to a byte array
     * @param value the body
     * @return a byte array
     * @throws MappingException if mapping causes in a serialization exception
     */
    byte[] write(Object value) throws MappingException;
}
