package space.sausage.vertxrest.core.registration;

import com.google.common.base.Strings;
import space.sausage.vertxrest.core.annotation.Consumes;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.annotation.Produces;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An adapter for a controller method
 */
public class ControllerMethod implements RouteParams {
    private final Object controller;
    private final Method method;

    private final Path path;
    private final Consumes consumes;
    private final Produces produces;

    private final List<Class<?>> argTypes;

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Consumes getConsumes() {
        return consumes;
    }

    @Override
    public Produces getProduces() {
        return produces;
    }

    /**
     * @return The method's argument types
     */
    public List<Class<?>> getArgTypes() {
        return argTypes;
    }

    /**
     * @param controller the controller instance
     * @param method the controller method
     */
    public ControllerMethod(Object controller, Method method) {
        this.controller = controller;
        this.method = method;

        path = method.getAnnotation(Path.class);
        consumes = method.getAnnotation(Consumes.class);
        produces = method.getAnnotation(Produces.class);

        argTypes = Arrays.asList(method.getParameterTypes());
    }

    /**
     * Invokes the underlying method
     * @param args the method's arguments
     * @return the result
     * @throws MethodInvokeException if calling the underlying method causes a reflection exception
     */
    public Object invoke(Object... args) throws MethodInvokeException {
        try {
            return method.invoke(controller, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MethodInvokeException("Unable to invoke method", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerMethod that = (ControllerMethod) o;
        return Objects.equals(controller, that.controller) &&
                Objects.equals(method, that.method) &&
                Objects.equals(path, that.path) &&
                Objects.equals(consumes, that.consumes) &&
                Objects.equals(produces, that.produces) &&
                Objects.equals(argTypes, that.argTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controller, method, path, consumes, produces, argTypes);
    }

    @Override
    public String toString() {
        String prefix = Strings.padEnd(String.format("[%s]", path.method()), 6, ' ');
        return String.format("%s %s", prefix, path.path());
    }
}
