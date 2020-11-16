package run.yuyang.trotsky.resource;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import run.yuyang.trotsky.service.ConfService;
import run.yuyang.trotsky.service.FileService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class IndexResource {

    @Inject
    ConfService confService;

    @Inject
    Vertx vertx;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> home() {
        return vertx.fileSystem().readFile(confService.getWorkerPath() + "/index.html")
                .onItem().transform(b -> b.toString("UTF-8"));
    }

    @GET
    @Path("/favicon.ico")
    @Produces("image/webp")
    public Buffer favicon() {
        return vertx.fileSystem().readFileBlocking("/META-INF/resources/img/logo.jpg");
    }

    @GET
    @Path("/{md}")
    @Produces("text/markdown")
    public Uni<String> getMd(@PathParam("md") String md) {
        return vertx.fileSystem().readFile(confService.getWorkerPath() + "/" + md)
                .onItem().transform(b -> b.toString("UTF-8"));
    }

}