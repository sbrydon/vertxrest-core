package space.sausage.vertxrest.core.handler.mapping;

/**
 * Indicates that serialization caused an exception
 */
public class MappingException extends Exception {
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
