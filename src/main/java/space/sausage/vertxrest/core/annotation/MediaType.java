package space.sausage.vertxrest.core.annotation;

/**
 * An enumeration of supported media types.
 * {@link #toString()} returns a string of type and subtype e.g. "text/plain"
 */
public enum MediaType {
    JSON("application/json"),
    PLAIN_TEXT("text/plain");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
