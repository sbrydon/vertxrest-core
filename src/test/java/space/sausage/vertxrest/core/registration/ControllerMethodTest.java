package space.sausage.vertxrest.core.registration;

import io.vertx.core.http.HttpMethod;
import org.junit.Test;
import space.sausage.vertxrest.core.annotation.Consumes;
import space.sausage.vertxrest.core.annotation.MediaType;
import space.sausage.vertxrest.core.annotation.Path;
import space.sausage.vertxrest.core.annotation.Produces;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerMethodTest {
    @Test
    public void ctorGetsMethodAnnotations() throws NoSuchMethodException {
        Controller controller = new Controller();

        ControllerMethod method = new ControllerMethod(controller, controller.method());

        assertThat(method.getPath()).isEqualTo(controller.path());
        assertThat(method.getConsumes()).isEqualTo(controller.consumes());
        assertThat(method.getProduces()).isEqualTo(controller.produces());
    }

    @Test
    public void invokeInvokesControllerMethod() throws MethodInvokeException, NoSuchMethodException {
        Controller controller = new Controller();

        Object result = new ControllerMethod(controller, controller.method()).invoke("world");

        assertThat(result).isEqualTo("hello world");
    }

    @Test
    public void equalsSameStateReturnsTrue() throws NoSuchMethodException {
        Controller controller = new Controller();

        Object methodOne = new ControllerMethod(controller, controller.method());
        Object methodTwo = new ControllerMethod(controller, controller.method());

        assertThat(methodOne).isEqualTo(methodTwo);
        assertThat(methodOne.hashCode()).isEqualTo(methodTwo.hashCode());
    }

    @Test
    public void equalsDifferentStateReturnsFalse() throws NoSuchMethodException {
        Controller controllerOne = new Controller();
        Controller controllerTwo = new Controller();

        Object methodOne = new ControllerMethod(controllerOne, controllerOne.method());
        Object methodTwo = new ControllerMethod(controllerTwo, controllerTwo.method());

        assertThat(methodOne).isNotEqualTo(methodTwo);
        assertThat(methodOne.hashCode()).isNotEqualTo(methodTwo.hashCode());
    }

    class Controller {
        @Path(method = HttpMethod.GET, path = "/path")
        @Produces(MediaType.PLAIN_TEXT)
        @Consumes(MediaType.PLAIN_TEXT)
        public String get(String name) {
            return "hello " + name;
        }

        Path path() throws NoSuchMethodException {
            return method().getAnnotation(Path.class);
        }

        Consumes consumes() throws NoSuchMethodException {
            return method().getAnnotation(Consumes.class);
        }

        Produces produces() throws NoSuchMethodException {
            return method().getAnnotation(Produces.class);
        }

        Method method() throws NoSuchMethodException {
            return getClass().getMethod("get", String.class);
        }
    }
}
