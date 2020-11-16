package run.yuyang.trotsky.resource;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author YuYang
 */
@Path("/admin")
public class AdminResource {

    @Inject
    Vertx vertx;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> home() {
        return vertx.fileSystem().readFile("/META-INF/resources/login.html")
                .onItem().transform(b -> b.toString("UTF-8"));
    }

}
