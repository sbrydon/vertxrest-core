package space.sausage.vertxrest.core.annotation;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The request method and path
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Path {
    /**
     * @return the HTTP method e.g. GET
     */
    HttpMethod method();

    /**
     * @return the route e.g. /pets/:id
     */
    String path() default "";
}
