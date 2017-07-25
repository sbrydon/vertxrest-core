A simple annotation-driven REST framework built on top of Vert.x-Web.

Controller example:
```java
public class Controller {
    @Path(method = HttpMethod.GET, path = "/hello/:name")
    @Produces(MediaType.PLAIN_TEXT)
    public String getHello(HttpServerRequest request) {
        String name = request.getParam("name");
        return String.format("Hello %s from vertx-rest!", name);
    }

    @Path(method = HttpMethod.POST, path = "/hello")
    @Consumes(MediaType.PLAIN_TEXT)
    @Produces(MediaType.PLAIN_TEXT)
    public String postHello(String name) {
        return String.format("Hello %s from vertx-rest!", name);
    }

    @Path(method = HttpMethod.POST, path = "/hello/person")
    @Consumes(MediaType.JSON)
    @Produces(MediaType.JSON)
    public List<Greeting> postPerson(Person person) {
        String body = String.format("Hello %s from vertx-rest!", person.getName());
        Greeting greeting = new Greeting(body);

        List<Greeting> response = new ArrayList<>();
        response.add(greeting);
        response.add(greeting);

        return foo;
    }
}
```

Configuration example:
```java
public class App {
    public static void main(String[] args) {
        VertxRest rest = new VertxRestBuilder()
                .controller(Controller.class)
                .port(42020)
                .build();

        rest.start();
    }
}
```
