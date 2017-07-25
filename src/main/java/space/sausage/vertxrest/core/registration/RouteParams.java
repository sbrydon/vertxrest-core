package space.sausage.vertxrest.core.registration;

import space.sausage.vertxrest.core.annotation.Consumes;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.annotation.Produces;

/**
 * Holds the values of a method's routing annotations
 */
public interface RouteParams {
    /**
     * @return the route's path e.g. /pets
     */
    Path getPath();

    /**
     * @return the route's request body
     * @see #hasConsumes()
     */
    Consumes getConsumes();

    /**
     * @return the route's response body
     * @see #hasProduces()
     */
    Produces getProduces();

    /**
     * @return true if the route has a request body
     */
    default boolean hasConsumes() {
        return getConsumes() != null;
    }

    /**
     * @return true if the route has a response body
     */
    default boolean hasProduces() {
        return getProduces() != null;
    }
}
