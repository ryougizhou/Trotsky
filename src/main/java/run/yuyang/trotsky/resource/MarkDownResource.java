package run.yuyang.trotsky.resource;

import run.yuyang.trotsky.model.request.MDParam;
import run.yuyang.trotsky.service.ConfService;
import run.yuyang.trotsky.service.FileService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author YuYang
 */
@Path("/md")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarkDownResource {

    @Inject
    ConfService confService;

    @Inject
    FileService fileService;

    @POST
    public Response newFile(MDParam param) {
        fileService.saveNewFile(confService.getWorkerPath() + confService.getNoteConfs().get(param.getFather()).getPath() + "/" + param.getName(), param.getText());
        return Response.ok("{\"msg\":\"success\"}").build();
    }

    @GET
    public Response getAll() {
        return Response.ok(confService.getNoteConfs()).build();
    }

//    @DELETE
//    @Path("/{name}")
//    public Response delNotes(@PathParam("name") String name) {
//
//    }

}
