package shine.http.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Server extends AbstractVerticle {

    public void start() {
        vertx.createHttpServer().requestHandler(req -> req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!")).listen(8001);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Server());
    }
}
