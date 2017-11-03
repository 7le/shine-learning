package shine.http.demo2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * rest http server
 */
public class Server extends AbstractVerticle{

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Server());
    }

    // Store our product
    private Map<Integer, Whisky> products = new LinkedHashMap<>();

    /**
     *  Create some product
     */
    private void createSomeData() {
        Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
        products.put(bowmore.getId(), bowmore);
        Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
        products.put(talisker.getId(), talisker);
    }

    private void addOne(RoutingContext routingContext) {
        final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(),
                Whisky.class);
        products.put(whisky.getId(), whisky);
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(whisky));
    }

    private void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Integer idAsInteger = Integer.valueOf(id);
            products.remove(idAsInteger);
        }
        routingContext.response().setStatusCode(204).end();
    }

    @Override
    public void start(Future<Void> fut) throws Exception {
        createSomeData();

        // Create a router object.
        Router router = Router.router(vertx);

        // Rest of the method
        router.route("/api/whiskies*").handler(BodyHandler.create());
        router.post("/api/whiskies").handler(this::addOne);
        router.get("/api/whiskies").handler(this::getAll);
        router.delete("/api/whiskies/:id").handler(this::deleteOne);

        vertx.createHttpServer().requestHandler(router::accept)
                .listen(
                        config().getInteger("http.port", 8003),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                                System.out.println(8003);
                            } else {
                                fut.fail(result.cause());
                            }
                        });
    }

    private void getAll(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(products.values()));
    }
}
